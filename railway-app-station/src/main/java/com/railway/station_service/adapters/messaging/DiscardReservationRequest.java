package com.railway.station_service.adapters.messaging;

import java.util.UUID;

public class DiscardReservationRequest {
	private UUID requestId;
	private Long timetableId;
	
	public DiscardReservationRequest(UUID requestId, Long timetableId) {
		this.requestId = requestId;
		this.timetableId = timetableId;
	}
	
	public UUID getRequestId() {
		return requestId;
	}
	
	public Long getTimetableId() {
		return timetableId;
	}
}
