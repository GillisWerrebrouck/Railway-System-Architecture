package com.railway.timetable_service.adapters.messaging;

import java.util.UUID;

public class RouteRequest {
	private UUID requestId;
	private Long timetableId;
	private Long routeId;
	
	public RouteRequest(Long timetableId, Long routeId) {
		this.requestId = UUID.randomUUID();
		this.timetableId = timetableId;
		this.routeId = routeId;
	}
	
	public UUID getRequestId() {
		return requestId;
	}
	
	public Long getTimetableId() {
		return timetableId;
	}
	
	public Long getRouteId() {
		return routeId;
	}
}
