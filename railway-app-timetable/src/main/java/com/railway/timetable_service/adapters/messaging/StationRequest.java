package com.railway.timetable_service.adapters.messaging;

import java.time.LocalDateTime;
import java.util.UUID;

public class StationRequest {
	private UUID requestId;
	private UUID stationId;
	private Long timetableId;
	private LocalDateTime arrivalDateTime;
	private LocalDateTime departureDateTime;
	
	public StationRequest(UUID stationId, Long timetableId, LocalDateTime arrivaDateTime, LocalDateTime departureDateTime) {
		this.requestId = UUID.randomUUID();
		this.stationId = stationId;
		this.timetableId = timetableId;
		this.arrivalDateTime = arrivaDateTime;
		this.departureDateTime = departureDateTime;
	}
	
	public UUID getRequestId() {
		return requestId;
	}
	
	public UUID getStationId() {
		return stationId;
	}
	
	public Long getTimetableId() {
		return timetableId;
	}
	
	public LocalDateTime getArrivalDateTime() {
		return arrivalDateTime;
	}
	
	public LocalDateTime getDepartureDateTime() {
		return departureDateTime;
	}
}
