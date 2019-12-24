package com.railway.station_service.adapters.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import com.railway.station_service.domain.StationService;

@Service
public class StationEventHandler {
	private static Logger logger = LoggerFactory.getLogger(StationEventHandler.class);
	private final StationService stationService;
	
	@Autowired
	public StationEventHandler(StationService stationService) {
		this.stationService = stationService;
	}
	
	@StreamListener(Channels.ROUTE_FETCHED)
	public void processFetchedRoute(RouteFetchedResponse response) {
		if(response.getRouteConnections().size() != 0) {
			logger.info("[Station Event Handler] successfully fetched route");
			this.stationService.routeFetched(response);
		} else {
			logger.info("[Station Event Handler] failed to fetch route");
			this.stationService.failedToFetchRoute(response);
		}
	}
	
	
}
