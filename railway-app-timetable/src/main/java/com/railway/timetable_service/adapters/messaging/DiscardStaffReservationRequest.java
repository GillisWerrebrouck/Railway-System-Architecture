package com.railway.timetable_service.adapters.messaging;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class DiscardStaffReservationRequest {
	private UUID requestId;
	private List<String> staffIds;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	
	public DiscardStaffReservationRequest(List<String> staffIds, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		this.requestId = UUID.randomUUID();
		this.staffIds = staffIds;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}
	
	public UUID getRequestId() {
		return requestId;
	}
	
	public List<String> getStaffIds() {
		return staffIds;
	}
	
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}
	
	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}
}
