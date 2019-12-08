package com.railway.timetable_service.adapters.messaging;

import java.util.Collection;
import java.util.UUID;

public class RouteFetchedResponse {
	private Long routeId;
	private Long timetableId;
	private UUID requestId;
	private Collection<RoutePart> routeConnections;
	private Route route;
	
	public RouteFetchedResponse(Long routeId, Long timetableId, UUID requestId, Collection<RoutePart> routeConnections, Route route) {
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
	
	public void setTimetableId(Long timeTableId) {
		this.timetableId = timeTableId;
	}
	
	public UUID getRequestId() {
		return requestId;
	}
	
	public void setRequestId(UUID requestId) {
		this.requestId = requestId;
	}
	
	public Collection<RoutePart> getRouteConnections() {
		return routeConnections;
	}
	
	public void setRouteConnections(Collection<RoutePart> routeConnections) {
		this.routeConnections = routeConnections;
	}
	
	public Route getRoute() {
		return route;
	}
	
	public void setRoute(Route route) {
		this.route = route;
	}
}
