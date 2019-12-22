package com.railway.timetable_service.adapters.messaging;

public class RouteUsageResponse {
	private Long connectionId;
	private boolean isUsed;
	private boolean active;

	public RouteUsageResponse(Long connectionId, boolean isUsed) {
		this.connectionId = connectionId;
		this.isUsed = isUsed;
		this.active = false;
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
}
