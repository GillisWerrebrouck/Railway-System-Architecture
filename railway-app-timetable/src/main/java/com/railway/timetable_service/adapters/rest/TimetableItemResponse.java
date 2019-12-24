package com.railway.timetable_service.adapters.rest;

import java.time.LocalDateTime;

public class TimetableItemResponse {
	private Long id;

	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;

	private int delay;
	private String reasonForDelay;

	private Long routeId;
	private String route;
	
	public TimetableItemResponse(Long id, Long routeId, String route, LocalDateTime startDateTime, LocalDateTime endDateTime, int delay, String reasonForDelay) {
		this.id = id;
		this.routeId = routeId;
		this.route = route;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.delay = delay;
		this.reasonForDelay = reasonForDelay;
	}
	
	public Long getId() {
		return id;
	}
	
	public Long getRouteId() {
		return routeId;
	}
	
	public String getRoute() {
		return route;
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
	
	public String getReasonForDelay() {
		return reasonForDelay;
	}
}
