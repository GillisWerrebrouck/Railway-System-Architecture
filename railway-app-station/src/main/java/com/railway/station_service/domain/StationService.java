package com.railway.station_service.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.station_service.adapters.messaging.DelayRequest;
import com.railway.station_service.adapters.messaging.MessageGateway;
import com.railway.station_service.adapters.messaging.Route;
import com.railway.station_service.adapters.messaging.RouteConnection;
import com.railway.station_service.adapters.messaging.RouteFetchedResponse;
import com.railway.station_service.adapters.messaging.RoutePart;
import com.railway.station_service.adapters.messaging.RouteRequest;
import com.railway.station_service.adapters.messaging.StationOnRoute;
import com.railway.station_service.adapters.messaging.UpdateDelayRequest;
import com.railway.station_service.persistence.DelayRequestRepository;
import com.railway.station_service.persistence.PlatformRepository;
import com.railway.station_service.persistence.ScheduleItemRepository;
import com.railway.station_service.persistence.StationRepository;

@Service
public class StationService {
	StationRepository stationRepository;
	PlatformRepository platformRepository;
	ScheduleItemRepository scheduleItemRepository;
	DelayRequestRepository delayRequestRepository;
	private final MessageGateway gateway;
	private static Logger logger = LoggerFactory.getLogger(StationService.class);
	int requestDelay = 0;
	
	@Autowired
	public StationService(StationRepository stationRepository, PlatformRepository platformRepository, ScheduleItemRepository scheduleItemRepository, DelayRequestRepository delayRequestRepository, MessageGateway gateway) {
		this.stationRepository = stationRepository;
		this.platformRepository = platformRepository;
		this.scheduleItemRepository = scheduleItemRepository;
		this.delayRequestRepository = delayRequestRepository;
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
			LocalDateTime arrivalTimeWithDelay = scheduleItem.getArrivalDateTime().plusMinutes(scheduleItem.getDelayInMinutes());
			LocalDateTime departureTimeWithDelay = scheduleItem.getDepartureDateTime().plusMinutes(scheduleItem.getDelayInMinutes());
			if(!(arrivalDateTime.isBefore(arrivalTimeWithDelay) && departureDateTime.isBefore(arrivalTimeWithDelay) || 
					arrivalDateTime.isAfter(departureTimeWithDelay) && departureDateTime.isAfter(departureTimeWithDelay))) {
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
		// to later check if received route fetched event is for this delayRequest
		delayRequest.setRouteRequestId(request.getRequestId());
		
		// save the delay request in the key-value store
		delayRequestRepository.save(delayRequest);
		
		gateway.getRoute(request);
	}

	// fetching routes to get all the stations on that route that will be affected by the delay
	public void routeFetched(RouteFetchedResponse routeFetchedResponse) {
		// get the delay request in the key-value store and delete it
		DelayRequest dr = delayRequestRepository.findById(routeFetchedResponse.getRequestId()).orElse(null);
		delayRequestRepository.deleteById(routeFetchedResponse.getRequestId());
		
		if(dr != null) {
			// find first station of route that will be affected by delay 
			// if no start station is present in delay request then all stations on the given route are notified of the delay
			StationOnRoute startStation = getStart(routeFetchedResponse.getRoute());
			
			// get following stations on that route
			List<StationOnRoute> stations = new ArrayList<>();
			StationOnRoute previous = startStation;
			logger.info("START STATION ID: "+ dr.getStartStationId().toString());
			boolean partOfDelay = false;
			if(dr.getStartStationId() == null || 
					startStation.getStationId().compareTo(dr.getStartStationId().toString()) == 0) {
				stations.add(startStation);
				partOfDelay = true;
			}
			
			StationOnRoute current = null;
			Collection<RoutePart> allRouteConnections = routeFetchedResponse.getRouteConnections();
			
			while(allRouteConnections.size() > 0) {
				RoutePart routePart = getNextRoutePart(allRouteConnections, UUID.fromString(previous.getStationId()));
				if(routePart == null) {
					break;
				}
				
				// remove route part for next iteration
				allRouteConnections.removeIf(r -> r.getId() == routePart.getId());
		
				if(routePart.getStationX().getStationId().compareTo(previous.getStationId()) == 0) {
					current = routePart.getStationY();
				} else {
					current = routePart.getStationX();
				}
				
				if(current.getStationId().compareTo(dr.getStartStationId().toString()) == 0) {
					partOfDelay = true;
				}
				
				if(partOfDelay) {
					stations.add(current);
				}
				previous=current;
			}
			getStationsFromDatabase(stations, dr);
		}
	}
	
	private StationOnRoute getStart(Route route) {
		// find the start station of the route
		StationOnRoute startStation = null;
		for(RouteConnection r : route.getRouteConnections()) {
			if(r.isStartOfRoute()) {
				startStation = r.getStation();
				break;
			}
		}
		return startStation;
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
	
	private void getStationsFromDatabase(List<StationOnRoute> stations, DelayRequest request) {
		for (StationOnRoute s : stations) {
			UUID id = UUID.fromString(s.getStationId());
			requestDelay = request.getDelayInMinutes();
			Station station = stationRepository.findById(id).orElse(null);
			if (station != null) {
				logger.info("[getStationsFromDatabase] station found in db " + station.getName());
				iteratePlatforms(station, request);
			}
		}
	}
	
	private void iteratePlatforms(Station station, DelayRequest request) {
		//check all platforms 
		for(Platform p : station.getPlatforms()) {
			logger.info("[getStationsFromDatabase] platform from station " + p.getPlatformNumber());
			iterateScheduleItems(p, request, station);
		}
	}
	
	private void iterateScheduleItems(Platform p, DelayRequest request, Station station) {
		//check all scheduleItems
		for(ScheduleItem item : p.getReservedSlots()) {
			logger.info("[getStationsFromDatabase] scheduleItem from platform " + item.getId());
			//if the right scheduleItem is found then this item is affected by the delay
			if(item.getTimetableId() == request.getTimetableId()) {
				addDelay(item, request, station, p);
			}
		}
	}
	
	private void addDelay(ScheduleItem item, DelayRequest request, Station station, Platform p) {
		logger.info("[getStationsFromDatabase] scheduleItem found");
		//original scheduleitem can already have delay -> so total delay is calculated
		int totalDelay = item.getDelayInMinutes() + requestDelay;
		int requestDelay = 0;
		LocalDateTime newArrivalTime = item.getArrivalDateTime().plusMinutes(totalDelay);
		//check if the new arrivaltime causes a conflict with another train on the same platform
		boolean overlap = newArrivalTimeOverlaps(p, newArrivalTime, item.getId());
		//if not set new delay and save
		if(!overlap) {
			item.setDelayInMinutes(totalDelay);
			scheduleItemRepository.save(item);
		} else {
			//search new platform starting on time = newArrivalTime 
			Object[] pair = searchNewTimeSlot(station, newArrivalTime,0, item.getId());
			//found platform, arrivaltime and additional delay when nothing was available on newArrivalTime
			LocalDateTime finalArrivalTime = (LocalDateTime) pair[0];
			Platform platform = (Platform) pair[1];
			int additionalDelay = (int) pair[2];
			//change and save scheduleItem
			item.setPlatform(platform);
			item.setDelayInMinutes(totalDelay + additionalDelay);
			scheduleItemRepository.save(item);
			informTimetable(request.getTimetableId(), additionalDelay);
		}
	}

	private void informTimetable(Long timetableId, int delayInMinutes) {
		// create delay request with timetableid and full delay in minutes
		UpdateDelayRequest request = new UpdateDelayRequest(timetableId, delayInMinutes);
		gateway.notifyExtraDelay(request);
		
	}

	private Object[] searchNewTimeSlot(Station station, LocalDateTime newArrivalTime, int minutes, Long id) {
		//iterate all platforms
		for (int i=0; i < station.getPlatforms().size(); i++) {
			Platform p = station.getPlatforms().get(i);
			//check if there is overlap here
			boolean overlap = newArrivalTimeOverlaps(p, newArrivalTime, id);
			if(!overlap) {
				Object[] pair = new Object [] {newArrivalTime, p, minutes};
				return pair;
			}
		}
		return searchNewTimeSlot(station, newArrivalTime.plusMinutes(1), minutes++, id); 
	}

	// returns true if there is no overlap, else false
	private boolean newArrivalTimeOverlaps(Platform p, LocalDateTime newArrivalTime, Long id) {
		for(ScheduleItem item : p.getReservedSlots()) {
			//current scheduleitem is not the one affected by the delay
			if(item.getId() != id) {
				LocalDateTime arrivalTimeWIthDelay = item.getArrivalDateTime().plusMinutes(item.getDelayInMinutes());
				LocalDateTime departureTimeWithDelay = item.getDepartureDateTime().plusMinutes(item.getDelayInMinutes());
				if(arrivalTimeWIthDelay.isBefore(newArrivalTime) && departureTimeWithDelay.isAfter(newArrivalTime)) {
					return true;
				}
				if(arrivalTimeWIthDelay.isBefore(newArrivalTime.plusMinutes(8)) && departureTimeWithDelay.isAfter(newArrivalTime.plusMinutes(8))) {
					return true;
				}
			}
		}
		return false;
	}

	public void failedToFetchRoute(RouteFetchedResponse response) {
		logger.info("[StationService] failed to fetch route");
	}
}
