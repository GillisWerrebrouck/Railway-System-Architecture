package com.railway.route_management_service.domain;

import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class RouteAssociation {

	private Long routeId;
	private Long connectionId;
	
	public RouteAssociation(Long routeId, Long connectionId) {
		this.routeId = routeId;
		this.connectionId = connectionId;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public Long getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(Long connectionId) {
		this.connectionId = connectionId;
	}
}
