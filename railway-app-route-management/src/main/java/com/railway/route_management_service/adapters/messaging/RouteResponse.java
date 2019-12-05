package com.railway.route_management_service.adapters.messaging;

import java.util.Collection;
import java.util.UUID;

import com.railway.route_management_service.domain.Connection;

public class RouteResponse {
	private Long routeId;
	private Long timeTableId;
	private UUID requestId;
	private Collection<Connection> route;
	
	public RouteResponse(Long routeId, Long timeTableId, UUID requestId, Collection<Connection> route) {
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
	
	public Collection<Connection> getRoute() {
		return route;
	}
	
	public void setRoute(Collection<Connection> route) {
		this.route = route; 
	}
}
