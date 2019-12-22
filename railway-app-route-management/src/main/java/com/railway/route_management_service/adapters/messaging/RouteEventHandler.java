package com.railway.route_management_service.adapters.messaging;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import com.railway.route_management_service.domain.RouteService;
import com.railway.route_management_service.domain.Station;
import com.railway.route_management_service.domain.StationService;

@Service
public class RouteEventHandler {
	private static Logger logger = LoggerFactory.getLogger(RouteCommandHandler.class);
	private final StationService stationService;
	private final RouteService routeService;

	@Autowired
	public RouteEventHandler(StationService stationService, RouteService routeService) {
		this.stationService = stationService;
		this.routeService = routeService;
	}
	
	@StreamListener(Channels.STATION_CREATED)
	public void stationCreated(StationRequest request) {
		logger.info("[Route Command Handler] station created event received");
		stationService.createStation(new Station(UUID.fromString(request.getId()), request.getName()));
		logger.info("[Route Command Handler] station created");
	}
	
	@StreamListener(Channels.STATION_DELETED)
	public void stationDeleted(StationRequest request) {
		logger.info("[Route Command Handler] station delete event received");
		stationService.deleteStation(new Station(UUID.fromString(request.getId())));
		logger.info("[Route Command Handler] station deleted");
	}
	
	@StreamListener(Channels.ROUTE_USAGE_CHECKED)
	public void routeUsageChecked(RouteUsageResponse response) {
		logger.info("[Route Event Handler] route usage checked");
		routeService.updateConnection(response);
	}
}
