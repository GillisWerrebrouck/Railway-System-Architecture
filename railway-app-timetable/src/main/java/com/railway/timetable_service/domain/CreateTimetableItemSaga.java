package com.railway.timetable_service.domain;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.timetable_service.adapters.messaging.MessageGateway;
import com.railway.timetable_service.adapters.messaging.RouteConnection;
import com.railway.timetable_service.adapters.messaging.RouteFetchedResponse;
import com.railway.timetable_service.adapters.messaging.RoutePart;
import com.railway.timetable_service.adapters.messaging.RouteRequest;
import com.railway.timetable_service.adapters.messaging.Station;
import com.railway.timetable_service.adapters.messaging.StationRequest;
import com.railway.timetable_service.adapters.messaging.TrainRequest;
import com.railway.timetable_service.adapters.messaging.TrainReservedResponse;
import com.railway.timetable_service.persistence.TimetableItemRepository;

@Service
public class CreateTimetableItemSaga {
	private static Logger logger = LoggerFactory.getLogger(CreateTimetableItemSaga.class);
	private final MessageGateway gateway;
	private final Set<CreateTimetableItemListener> listeners;
	private final TimetableItemRepository timetableItemRepository;
	
	@Autowired
	public CreateTimetableItemSaga(MessageGateway gateway, TimetableItemRepository timetableItemRepository) {
		this.gateway = gateway;
		this.timetableItemRepository = timetableItemRepository;
		this.listeners = new HashSet<>(5);
	}
	
	public void registerListener(CreateTimetableItemListener listener) {
		this.listeners.add(listener);
	}
	
	public void startCreateTimetableItemSaga(TimetableItem timetableItem, TimetableRequest timetableRequest) {
		logger.info("[Create Timetable Item Saga] saga initiated");
		
		RouteRequest routeRequest = new RouteRequest(timetableItem.getId(), timetableItem.getRouteId());
		logger.info("[Create Timetable Item Saga] get route command sent");
		gateway.getRoute(routeRequest);
	}
	
	public void onRouteFetched(TimetableItem timetableItem, RouteFetchedResponse routeFetchedResponse) {
		logger.info("[Create Timetable Item Saga] successfully fetched route");
		
		// find the start station of the route
		Station startStation = null;
		for(RouteConnection r : routeFetchedResponse.getRoute().getRouteConnections()) {
			if(r.isStartOfRoute()) {
				startStation = r.getStation();
				break;
			}
		}

		if(startStation == null) this.createTimetableItemFailed(timetableItem);
		
		Collection<RoutePart> allRouteConnections = routeFetchedResponse.getRouteConnections();
		reserveAllStations(timetableItem, startStation, allRouteConnections);
		
		TrainRequest trainRequest = new TrainRequest(timetableItem.getId(), timetableItem.getStartDateTime(), timetableItem.getEndDateTime(), timetableItem.getRequestedTrainType());
		logger.info("[Create Timetable Item Saga] reserve train command sent");
		gateway.reserveTrain(trainRequest);
		
		// TODO: reserve staff
	}
	
	private void reserveAllStations(TimetableItem timetableItem, Station startStation, Collection<RoutePart> allRouteConnections) {
		// reserve a platform at the start station with a wait time of 15 minutes
		LocalDateTime departureTime = timetableItem.getStartDateTime();
		timetableItem.setStartDateTime(departureTime.minusMinutes(15));
		LocalDateTime arrivalTime = timetableItem.getStartDateTime();
		reserveStation(startStation, timetableItem.getId(), arrivalTime, departureTime);
		
		UUID previousStationId = UUID.fromString(startStation.getStationId());
		
		Station station = null;
		// loop over all stations on the route
		while(allRouteConnections.size() > 0) {
			RoutePart routePart = getNextRoutePart(allRouteConnections, previousStationId);
			// remove route part for next iteration
			allRouteConnections.removeIf(r -> r.getId() == routePart.getId());
			
			Long distance = routePart.getDistance();
			double maxSpeed = routePart.getMaxSpeed();
			// the travel time in minutes for a route part is the distance times 95% of the maximum speed on that route part
			double travelTime = Math.ceil(distance / ((maxSpeed*0.95)/60));
			
			arrivalTime = departureTime.plusMinutes((long)travelTime);
			departureTime = arrivalTime.plusMinutes(8);
			
			if(routePart.getStationX().getStationId().compareTo(previousStationId.toString()) == 0) {
				station = routePart.getStationY();
			} else {
				station = routePart.getStationX();
			}
			previousStationId=UUID.fromString(station.getStationId());
			
			// reserve a platform at the station with a wait time of 8 minutes
			reserveStation(station, timetableItem.getId(), arrivalTime, departureTime);
			timetableItem.setEndDateTime(departureTime);
		}
		timetableItemRepository.save(timetableItem);
	}
	
	private RoutePart getNextRoutePart(Collection<RoutePart> allRouteParts, UUID previousStationId) {
		RoutePart routePart = null;
		for(RoutePart r : allRouteParts) {
			if(r.getStationX().getStationId().compareTo(previousStationId.toString()) == 0 || 
					r.getStationY().getStationId().compareTo(previousStationId.toString()) == 0) {
				routePart = r;
			}
		}
		return routePart;
	}
	
	private void reserveStation(Station station, Long timetableId, LocalDateTime arrivalTime, LocalDateTime departureDateTime) {
		StationRequest stationRequest = new StationRequest(UUID.fromString(station.getStationId()), timetableId, arrivalTime, departureDateTime);
		logger.info("[Create Timetable Item Saga] reserve station command sent");
		gateway.reserveStation(stationRequest);
	}
	
	public void onTrainReserved(TimetableItem timetableItem, TrainReservedResponse trainReservedResponse) {
		logger.info("[Create Timetable Item Saga] successfully reserved train");

		this.createTimetableItemComplete(timetableItem);
	}
	
	public void onGetRouteFailed(TimetableItem timetableItem){
		logger.info("[Create Timetable Item Saga] failed to get route");
		this.createTimetableItemFailed(timetableItem);
	}
		
	public void onReserveTrainFailed(TimetableItem timetableItem){
		logger.info("[Create Timetable Item Saga] failed to reserve train");
		this.createTimetableItemFailed(timetableItem);
	}
	
	public void createTimetableItemComplete(TimetableItem timetableItem) {
		logger.info("[Create Timetable Item Saga] successfully created a timetable item");
		this.gateway.emitCreateTimetableItemCompleted(timetableItem);
		this.listeners.forEach(l -> l.onCreateTimetableItemResult(timetableItem));
	}
	
	public void createTimetableItemFailed(TimetableItem timetableItem) {
		logger.info("[Create Timetable Item Saga] failed to create a timetable item");
		this.listeners.forEach(l -> l.onCreateTimetableItemResult(timetableItem));
		timetableItemRepository.delete(timetableItem);
	}
}
