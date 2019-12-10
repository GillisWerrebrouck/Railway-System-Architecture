package com.railway.train_service.adapters.messaging;

import java.time.LocalDateTime;
import java.util.UUID;

import com.railway.train_service.domain.TrainType;

public class TrainRequest {
	private UUID requestId;
	private Long timetableId;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private TrainType trainType;
	
	public TrainRequest(UUID requestId, Long timetableId, LocalDateTime startDateTime, LocalDateTime endDateTime, TrainType trainType) {
		this.requestId = requestId;
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
