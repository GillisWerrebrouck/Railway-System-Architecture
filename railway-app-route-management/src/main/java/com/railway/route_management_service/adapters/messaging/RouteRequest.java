package com.railway.route_management_service.adapters.messaging;

import java.util.UUID;

public class RouteRequest {
	private UUID requestId;
	private Long routeId;
	private Long timetableId;
	
	public RouteRequest(UUID requestId, Long routeId, Long timetableId) {
		this.requestId = requestId;
		this.routeId = routeId;
		this.timetableId = timetableId;
	}
	
	public UUID getRequestId() {
		return requestId;
	}
	
	public Long getRouteId() {
		return routeId;
	}
	
	public Long getTimetableId() {
		return timetableId;
	}
}
