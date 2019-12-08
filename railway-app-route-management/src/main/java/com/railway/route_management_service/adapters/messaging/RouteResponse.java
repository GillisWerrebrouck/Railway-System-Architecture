package com.railway.route_management_service.adapters.messaging;

import java.util.Collection;
import java.util.UUID;

import com.railway.route_management_service.domain.Connection;
import com.railway.route_management_service.domain.Route;

public class RouteResponse {
	private Long routeId;
	private Long timetableId;
	private UUID requestId;
	private Collection<Connection> routeConnections;
	private Route route;
	
	public RouteResponse(Long routeId, Long timetableId, UUID requestId, Collection<Connection> routeConnections, Route route) {
		this.routeId = routeId;
		this.timetableId = timetableId;
		this.requestId = requestId;
		this.routeConnections = routeConnections;
		this.route = route;
	}
	
	public Long getRouteId() {
		return routeId;
	}
	
	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
	
	public Long getTimetableId() {
		return timetableId;
	}
	
	public void setTimetableId(Long timetableId) {
		this.timetableId = timetableId;
	}
	
	public UUID getRequestId() {
		return requestId;
	}
	
	public void setRequestId(UUID requestId) {
		this.requestId = requestId;
	}
	
	public Collection<Connection> getRouteConnections() {
		return routeConnections;
	}
	
	public void setRouteConnections(Collection<Connection> routeConnections) {
		this.routeConnections = routeConnections;
	}
	
	public Route getRoute() {
		return route;
	}
	
	public void setRoute(Route route) {
		this.route = route;
	}
}
