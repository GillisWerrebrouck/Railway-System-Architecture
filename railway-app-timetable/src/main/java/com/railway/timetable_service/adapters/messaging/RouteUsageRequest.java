package com.railway.timetable_service.adapters.messaging;

import java.util.List;

public class RouteUsageRequest {
	private List<Long> routeIds;
	private Long connectionId;
	private boolean isUsed;

	public RouteUsageRequest(List<Long> routeIds, Long connectionId, boolean isUsed) {
		this.routeIds = routeIds;
		this.connectionId = connectionId;
		this.isUsed = isUsed;
	}

	public Long getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(Long connectionId) {
		this.connectionId = connectionId;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public List<Long> getRouteIds() {
		return routeIds;
	}

	public void setRouteIds(List<Long> routeId) {
		this.routeIds = routeId;
	}
}
