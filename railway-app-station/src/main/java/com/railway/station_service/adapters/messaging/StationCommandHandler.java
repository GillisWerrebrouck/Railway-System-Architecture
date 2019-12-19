package com.railway.station_service.adapters.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
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
	
	@StreamListener(Channels.RESERVE_STATIONS)
	@SendTo(Channels.STATIONS_RESERVED)
	public StationsResponse reserveStation(StationsRequest stationsRequest) {
		logger.info("[Station Command Handler] reserve station command received");
		
		for (StationRequest request : stationsRequest.getStationRequests()) {
			boolean isReserved = stationService.reserveStation(request.getStationId(), request.getTimetableId(), request.getArrivalDateTime(), request.getDepartureDateTime());
			if(!isReserved) {
				stationService.discardStationReservations(request.getTimetableId());
				logger.info("[Station Command Handler] stations could not be reserved");
				StationsResponse response = new StationsResponse(stationsRequest.getRequestId(), stationsRequest.getTimetableId(), false);
				return response;
			}
		}
		logger.info("[Station Command Handler] stations reserved");
		StationsResponse response = new StationsResponse(stationsRequest.getRequestId(), stationsRequest.getTimetableId(), true);
		return response;
	}
	
	@StreamListener(Channels.DISCARD_STATION_RESERVATIONS)
	public void discardTrainReservation(DiscardReservationRequest request) {
		logger.info("[Station Command Handler] discard station reservations command received");
		stationService.discardStationReservations(request.getTimetableId());
	}
	
	@StreamListener(Channels.NOTIFY_DELAY)
	public void notifyDelay(DelayRequest request) {
		logger.info("[Station Command Handler] notify delay command received");
		stationService.processDelay(request);
	}
	
}
