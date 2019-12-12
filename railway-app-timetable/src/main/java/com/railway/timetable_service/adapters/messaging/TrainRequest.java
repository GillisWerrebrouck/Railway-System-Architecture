package com.railway.timetable_service.adapters.messaging;

import java.time.LocalDateTime;
import java.util.UUID;

public class TrainRequest {
	private UUID requestId;
	private Long timetableId;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private TrainType trainType;
	
	public TrainRequest(Long timetableId, LocalDateTime startDateTime, LocalDateTime endDateTime, TrainType trainType) {
		this.requestId = UUID.randomUUID();
		this.timetableId = timetableId;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.trainType = trainType;
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
	
	public TrainType getTrainType() {
		return trainType;
	}
}
