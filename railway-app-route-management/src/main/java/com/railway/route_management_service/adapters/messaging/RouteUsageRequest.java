package com.railway.route_management_service.adapters.messaging;

import java.util.List;

public class RouteUsageRequest {
	private List<Long> routeIds;
	private Long connectionId;

	public RouteUsageRequest(List<Long> routeIds, Long connectionId) {
		this.routeIds = routeIds;
		this.connectionId = connectionId;
	}

	public Long getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(Long connectionId) {
		this.connectionId = connectionId;
	}

	public List<Long> getRouteIds() {
		return routeIds;
	}

	public void setRouteId(List<Long> routeIds) {
		this.routeIds = routeIds;
	}
}
