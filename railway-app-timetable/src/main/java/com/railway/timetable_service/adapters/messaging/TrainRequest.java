package com.railway.timetable_service.adapters.messaging;

import java.time.LocalDateTime;
import java.util.UUID;

public class TrainRequest {
	private UUID requestId;
	private Long timetableId;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	
	public TrainRequest(Long timetableId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		this.requestId = UUID.randomUUID();
		this.timetableId = timetableId;
		this.startDateTime = startDateTime;
		this.startDateTime = endDateTime;
	}
	
	public UUID getRequestId() {
		return requestId;
	}
	
	public Long getTimetableId() {
		return timetableId;
	}
	
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}
	
	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}
}
