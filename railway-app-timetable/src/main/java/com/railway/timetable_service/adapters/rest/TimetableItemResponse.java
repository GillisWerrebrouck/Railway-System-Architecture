package com.railway.timetable_service.adapters.rest;

import java.time.LocalDateTime;

public class TimetableItemResponse {
	private Long id;

	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	
	private int delay;

	private Long routeId;
	private String route;
	
	public TimetableItemResponse(Long id, LocalDateTime startDateTime, LocalDateTime endDateTime, int delay, Long routeId, String route) {
		this.id = id;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.delay = delay;
		this.routeId = routeId;
		this.route = route;
	}
	
	public Long getId() {
		return id;
	}
	
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}
	
	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}
	
	public int getDelay() {
		return delay;
	}
	
	public Long getRouteId() {
		return routeId;
	}
	
	public String getRoute() {
		return route;
	}
}
