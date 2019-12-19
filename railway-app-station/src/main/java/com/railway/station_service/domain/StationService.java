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
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.railway.station_service.adapters.messaging.DelayRequest;
import com.railway.station_service.adapters.messaging.DiscardReservationRequest;
import com.railway.station_service.adapters.messaging.MessageGateway;
import com.railway.station_service.adapters.messaging.RouteConnection;
import com.railway.station_service.adapters.messaging.RouteFetchedResponse;
import com.railway.station_service.adapters.messaging.RoutePart;
import com.railway.station_service.adapters.messaging.RouteRequest;
import com.railway.station_service.adapters.messaging.StationOnRoute;
import com.railway.station_service.adapters.messaging.UpdateDelayRequest;
import com.railway.station_service.persistence.PlatformRepository;
import com.railway.station_service.persistence.ScheduleItemRepository;
import com.railway.station_service.persistence.StationRepository;

@Service
public class StationService {
	StationRepository stationRepository;
	PlatformRepository platformRepository;
	ScheduleItemRepository scheduleItemRepository;
	private Map<UUID, DelayRequest> requests;
	private final MessageGateway gateway;
	private static Logger logger = LoggerFactory.getLogger(StationService.class);
	
	@Autowired
	public StationService(StationRepository stationRepository, PlatformRepository platformRepository, ScheduleItemRepository scheduleItemRepository, MessageGateway gateway) {
		this.stationRepository = stationRepository;
		this.platformRepository = platformRepository;
		this.scheduleItemRepository = scheduleItemRepository;
		this.gateway = gateway;
		this.requests =  new HashMap<>();
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
			getStationsFromDatabase(stations, dr);
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
	
	private void getStationsFromDatabase(List<StationOnRoute> stations, DelayRequest request) {
		for (StationOnRoute s : stations) {
			UUID id = UUID.fromString(s.getStationId());
			Station station = stationRepository.findById(id).get();
			if (station != null) {
				logger.info("[getStationsFromDatabase] station found in db " + station.getName());
				//check all platforms 
				for(Platform p : station.getPlatforms()) {
					logger.info("[getStationsFromDatabase] platform from station " + p.getPlatformNumber());
					//check all scheduleItems
					for(ScheduleItem item : p.getReservedSlots()) {
						logger.info("[getStationsFromDatabase] scheduleItem from platform " + item.getId());
						//if the right scheduleItem is found then this item is affected by the delay
						if(item.getTimetableId() == request.getTimetableId()) {
							logger.info("[getStationsFromDatabase] scheduleItem found ");
							//original scheduleitem can already have delay -> so total delay is calculated
							int totalDelay = item.getDelayInMinutes() + request.getDelayInMinutes();
							//logger.info("" + totalDelay);
							LocalDateTime newArrivalTime = item.getArrivalDateTime().plusMinutes(totalDelay);
							//check if the new arrivaltime causes a conflict with another train on the same platform
							boolean overlap = overlaps(p, newArrivalTime, item.getId());
							//if not set new delay and save
							if(!overlap) {
								item.setDelayInMinutes(totalDelay);
								scheduleItemRepository.save(item);
								
							}
							else {
								//search new platform starting on time = newArrivalTime 
								Object[] pair = searchNewTimeSlot(station, newArrivalTime,0, item.getId());
								//found platform, arrivaltime and additional delay when nothing was available on newArrivalTime
								LocalDateTime finalArrivalTime = (LocalDateTime) pair[0];
								Platform platform = (Platform) pair[1];
								int additionalDelay = (int) pair[2];
								//change and save scheduleItem
								item.setPlatform(platform);
								// NOG BEKIJKEN
								item.setDelayInMinutes((totalDelay + additionalDelay)/2);
								scheduleItemRepository.save(item);
								informTimetable(request.getTimetableId(), totalDelay + additionalDelay);
							}
						}
					}
				}
			}
		}
	}

	private void informTimetable(Long timetableId, int delayInMinutes) {
		// create delay request with timetableid and full delay in minutes
		UpdateDelayRequest request = new UpdateDelayRequest(timetableId, delayInMinutes);
		gateway.notifyDelay(request);
		
	}

	private Object[] searchNewTimeSlot(Station station, LocalDateTime newArrivalTime, int minutes, Long id) {
		int i = 0;
		//iterate all platforms
		while (i < station.getPlatforms().size()) {
			Platform p = station.getPlatforms().get(i);
			//check if there is overlap here
			boolean overlap = overlaps(p, newArrivalTime, id);
			if(!overlap) {
				Object[] pair = new Object [] {newArrivalTime, p, minutes};
				return pair;
			}
			i++;
		}
		return searchNewTimeSlot(station, newArrivalTime.plusMinutes(1), minutes++, id);
	}

	// returns true if there is no overlap, else false
	private boolean overlaps(Platform p, LocalDateTime newArrivalTime, Long id) {
		for(ScheduleItem item : p.getReservedSlots()) {
			//current scheduleitem is not the one affected by the delay
			if(item.getId() != id) {
				if(item.getArrivalDateTime().isBefore(newArrivalTime) && item.getDepartureDateTime().isAfter(newArrivalTime)) {
					return true;
				}
				if(item.getArrivalDateTime().isBefore(newArrivalTime.plusMinutes(8)) && item.getDepartureDateTime().isAfter(newArrivalTime.plusMinutes(8))) {
					return true;
				}
			}
		}
		return false;
	}

	public void failedToFetchRoute(RouteFetchedResponse response) {
		logger.info("mislukt");
		
	}
}
