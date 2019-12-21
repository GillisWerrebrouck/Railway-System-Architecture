package com.railway.route_management_service.adapters.messaging;

public class RouteUsageResponse {
	private Long connectionId;
	private boolean isUsed;
	// new state
	private boolean active;

	public RouteUsageResponse(Long connectionId, boolean isUsed, boolean active) {
		this.connectionId = connectionId;
		this.isUsed = isUsed;
		this.active = active;
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
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
}
