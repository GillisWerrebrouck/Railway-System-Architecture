package com.railway.timetable_service.domain;

import java.time.LocalDateTime;

public class TimetableRequest {
	private Long routeId;
	private LocalDateTime startDateTime;
	
	public TimetableRequest(Long routeId, LocalDateTime startDateTime) {
		this.routeId = routeId;
		this.startDateTime = startDateTime;
	}
	
	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
	
	public Long getRouteId() {
		return routeId;
	}
	
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}
	
	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}
}
