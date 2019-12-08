package com.railway.station_service.adapters.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import com.railway.station_service.domain.StationService;

@Service
public class StationCommandHandler {
	private static Logger logger = LoggerFactory.getLogger(StationCommandHandler.class);
	private final StationService stationService;

	@Autowired
	public StationCommandHandler(StationService stationService) {
		this.stationService = stationService;
	}
	
	@StreamListener(Channels.RESERVE_STATION)
	public void reserveStation(StationRequest request) {
		logger.info("[Station Command Handler] reserve station command received");
		stationService.reserveStation(request.getStationId(), request.getTimetableId(), request.getArrivalDateTime(), request.getDepartureDateTime());
		logger.info("[Station Command Handler] station reserved");
	}
}
