package com.railway.timetable_service.adapters.messaging;

import java.time.LocalDateTime;
import java.util.UUID;

public class StationRequest {
	private UUID stationId;
	private LocalDateTime arrivalDateTime;
	private LocalDateTime departureDateTime;
	
	public StationRequest(UUID stationId, LocalDateTime arrivaDateTime, LocalDateTime departureDateTime) {
		this.stationId = stationId;
		this.arrivalDateTime = arrivaDateTime;
		this.departureDateTime = departureDateTime;
	}
	
	public UUID getStationId() {
		return stationId;
	}
	
	public LocalDateTime getArrivalDateTime() {
		return arrivalDateTime;
	}
	
	public LocalDateTime getDepartureDateTime() {
		return departureDateTime;
	}
}
