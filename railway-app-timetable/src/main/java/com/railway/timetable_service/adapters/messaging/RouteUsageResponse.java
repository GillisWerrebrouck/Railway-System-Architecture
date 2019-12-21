package com.railway.timetable_service.adapters.messaging;

public class RouteUsageResponse {
	private Long routeId;
	private Long connectionId;
	private boolean isUsed;

	public RouteUsageResponse(Long routeId, Long connectionId, boolean isUsed) {
		this.routeId = routeId;
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

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
}
