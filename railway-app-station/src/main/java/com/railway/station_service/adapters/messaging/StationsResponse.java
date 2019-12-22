package com.railway.station_service.adapters.messaging;

import java.util.UUID;

public class StationsResponse {
	private UUID requestId;
	private Long timetableId;
	private boolean reservationSuccessful;
	
	public StationsResponse(UUID requestId, Long timetableId, boolean reservationSuccessful) {
		this.requestId = requestId;
		this.timetableId = timetableId;
		this.reservationSuccessful = reservationSuccessful;
	}
	
	public UUID getRequestId() {
		return requestId;
	}
	
	public Long getTimetableId() {
		return timetableId;
	}
	
	public boolean isReservationSuccessful() {
		return reservationSuccessful;
	}
}
