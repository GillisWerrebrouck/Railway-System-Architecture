package com.railway.timetable_service.domain;

import java.time.LocalDateTime;

import com.railway.timetable_service.adapters.messaging.TrainType;

public class TimetableRequest {
	private Long routeId;
	private LocalDateTime startDateTime;
	private TrainType requestedTrainType;
	
	public TimetableRequest(Long routeId, LocalDateTime startDateTime, TrainType requestedTrainType) {
		this.routeId = routeId;
		this.startDateTime = startDateTime;
		this.requestedTrainType = requestedTrainType;
	}
	
	public Long getRouteId() {
		return routeId;
	}
	
	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
	
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}
	
	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}
	
	public TrainType getRequestedTrainType() {
		return requestedTrainType;
	}
	
	public void setRequestedTrainType(TrainType trainType) {
		this.requestedTrainType = trainType;
	}
}
