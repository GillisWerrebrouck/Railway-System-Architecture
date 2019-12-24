package com.railway.staff_service.adapters.messaging;

import java.util.UUID;

public class DiscardStaffReservationRequest {
	private UUID requestId;
	
	@SuppressWarnings("unused")
	private DiscardStaffReservationRequest() {}
	
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
