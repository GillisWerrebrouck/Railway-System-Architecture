package com.railway.station_service.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.station_service.adapters.messaging.DelayRequest;
import com.railway.station_service.adapters.messaging.DiscardReservationRequest;
import com.railway.station_service.adapters.messaging.MessageGateway;
import com.railway.station_service.adapters.messaging.RouteConnection;
import com.railway.station_service.adapters.messaging.RouteFetchedResponse;
import com.railway.station_service.adapters.messaging.RoutePart;
import com.railway.station_service.adapters.messaging.RouteRequest;
import com.railway.station_service.adapters.messaging.StationOnRoute;
import com.railway.station_service.persistence.PlatformRepository;
import com.railway.station_service.persistence.ScheduleItemRepository;
import com.railway.station_service.persistence.StationRepository;

@Service
public class StationService {
	StationRepository stationRepository;
	PlatformRepository platformRepository;
	ScheduleItemRepository scheduleItemRepository;
	private Map<UUID, DelayRequest> requests = new HashMap<>();
	private final MessageGateway gateway;
	private static Logger logger = LoggerFactory.getLogger(StationService.class);
	
	@Autowired
	public StationService(StationRepository stationRepository, PlatformRepository platformRepository, ScheduleItemRepository scheduleItemRepository, MessageGateway gateway) {
		this.stationRepository = stationRepository;
		this.platformRepository = platformRepository;
		this.scheduleItemRepository = scheduleItemRepository;
		this.gateway = gateway;
	}
	
	public boolean reserveStation(UUID stationId, Long timetableId, LocalDateTime arrivalDateTime, LocalDateTime departureDateTime) {
		Station station = stationRepository.findById(stationId).orElse(null);
		if(station == null) return false;
		
		Collection<Platform> platforms = station.getPlatforms();
		for(Platform platform : platforms) {
			if(isPlatformAvailable(platform.getReservedSlots(), arrivalDateTime, departureDateTime)) {
				ScheduleItem scheduleItem = new ScheduleItem(timetableId, arrivalDateTime, departureDateTime);
				scheduleItem.setPlatform(platform);
				scheduleItemRepository.save(scheduleItem);
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isPlatformAvailable(Collection<ScheduleItem> reservedSlots, LocalDateTime arrivalDateTime, LocalDateTime departureDateTime) {
		boolean isPlatformAvailable = true;
		
		for(ScheduleItem scheduleItem : reservedSlots) {
			if(!(arrivalDateTime.isBefore(scheduleItem.getArrivalDateTime()) && departureDateTime.isBefore(scheduleItem.getArrivalDateTime()) || 
					arrivalDateTime.isAfter(scheduleItem.getDepartureDateTime()) && departureDateTime.isAfter(scheduleItem.getDepartureDateTime()))) {
				isPlatformAvailable = false;
			}
		}
		
		return isPlatformAvailable;
	}

	public void discardStationReservations(Long timetableId) {
		scheduleItemRepository.deleteAllByTimetableId(timetableId);
	}
	
	public void processDelay(DelayRequest delayRequest) {
		
		RouteRequest request = new RouteRequest(delayRequest.getTimetableId(), delayRequest.getRouteId());
		delayRequest.setRouteRequestId(request.getRequestId());
		requests.put(delayRequest.getRouteRequestId(), delayRequest);
		gateway.getRoute(request);
		//gateway.delayOccured(delayRequest);
	}

	//fetching routes to get all the stations on that route that will be affected by the delay
	public void routeFetched(RouteFetchedResponse routeFetchedResponse) {
		//get delayrequest corresponding to this route fetch call
		DelayRequest dr = requests.containsKey(routeFetchedResponse.getRequestId())? requests.get(routeFetchedResponse.getRequestId()) : null;
		if( dr != null) {
			logger.info("lukt");
			
			//find first station of route that will be affected by delay 
			//if no start station is present in delay request then all stations on the given route are notified of the delay
			StationOnRoute startStation = null;
			if(dr.getStartStationId() != null) {
				for(RouteConnection rc : routeFetchedResponse.getRoute().getRouteConnections()) {
					if(rc.getStation().getId() == dr.getStartStationId()) {
						startStation = rc.getStation();
						break;
					}
				}
			} else {
				for(RouteConnection rc : routeFetchedResponse.getRoute().getRouteConnections()) {
					if(rc.isStartOfRoute()) {
						startStation = rc.getStation();
						break;
					}
				}
			}
			
			//get following stations on that route
			List<StationOnRoute> stations = new ArrayList<>();
			StationOnRoute previous = startStation;
			stations.add(startStation);
			StationOnRoute current = null;
			Collection<RoutePart> allRouteConnections = routeFetchedResponse.getRouteConnections();
			while(allRouteConnections.size() > 0) {
				RoutePart routePart = getNextRoutePart(allRouteConnections, UUID.fromString(previous.getStationId()));
				// remove route part for next iteration
				allRouteConnections.removeIf(r -> r.getId() == routePart.getId());
		
				if(routePart.getStationX().getStationId().compareTo(previous.getStationId()) == 0) {
					current = routePart.getStationY();
				} else {
					current = routePart.getStationX();
				}
				stations.add(current);
				previous=current;
				
				
			}
			logger.info(stations.toString());
		}
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

	public void failedToFetchRoute(RouteFetchedResponse response) {
		logger.info("mislukt");
		
	}
}
