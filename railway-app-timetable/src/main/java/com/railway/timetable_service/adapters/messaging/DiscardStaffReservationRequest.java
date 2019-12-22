package com.railway.timetable_service.adapters.messaging;

import java.util.UUID;

public class DiscardStaffReservationRequest {
	private UUID requestId;
	
	public DiscardStaffReservationRequest(UUID requestId) {
		this.requestId = requestId;
	}
	
	public UUID getRequestId() {
		return requestId;
	}

	public void setRequestId(UUID requestId) {
		this.requestId = requestId;
	}
}
