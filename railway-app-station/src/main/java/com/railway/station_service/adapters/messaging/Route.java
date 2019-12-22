package com.railway.station_service.adapters.messaging;

import java.util.Collection;

public class Route {
	private Long id;
	private String name;
	private Collection<RouteConnection> routeConnections;
	
	public Route(Long id, String name, Collection<RouteConnection> routeConnections) {
		this.id = id;
		this.name = name;
		this.routeConnections = routeConnections;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Collection<RouteConnection> getRouteConnections() {
		return routeConnections;
	}
}
