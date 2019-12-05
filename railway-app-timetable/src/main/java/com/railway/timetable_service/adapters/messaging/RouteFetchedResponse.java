package com.railway.timetable_service.adapters.messaging;

import java.util.Collection;
import java.util.UUID;

public class RouteFetchedResponse {
	private Long routeId;
	private Long timeTableId;
	private UUID requestId;
	private Collection<RoutePart> route;
	
	public RouteFetchedResponse(Long routeId, Long timeTableId, UUID requestId, Collection<RoutePart> route) {
		this.routeId = routeId;
		this.timeTableId = timeTableId;
		this.requestId = requestId;
		this.route = route;
	}
	
	public Long getRouteId() {
		return routeId;
	}
	
	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
	
	public Long getTimeTableId() {
		return timeTableId;
	}
	
	public void setTimeTableId(Long timeTableId) {
		this.timeTableId = timeTableId;
	}
	
	public UUID getRequestId() {
		return requestId;
	}
	
	public void setRequestId(UUID requestId) {
		this.requestId = requestId;
	}
	
	public Collection<RoutePart> getRoute() {
		return route;
	}
	
	public void setRoute(Collection<RoutePart> route) {
		this.route = route; 
	}
}
