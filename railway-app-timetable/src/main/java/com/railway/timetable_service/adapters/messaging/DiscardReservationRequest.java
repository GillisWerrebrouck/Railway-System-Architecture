package com.railway.timetable_service.adapters.messaging;

import java.util.UUID;

public class DiscardReservationRequest {
	private UUID requestId;
	private Long timetableId;
	
	public DiscardReservationRequest(Long timetableId) {
		this.requestId = UUID.randomUUID();
		this.timetableId = timetableId;
	}
	
	public UUID getRequestId() {
		return requestId;
	}
	
	public Long getTimetableId() {
		return timetableId;
	}
}
