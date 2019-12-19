package com.railway.route_management_service.adapters.rest;

import java.util.List;

public class UpdateRequest {

	private List<Long> connectionIds;
	private boolean active;
	
	public UpdateRequest(List<Long> connectionIds, boolean active) {
		this.connectionIds = connectionIds;
		this.active = active;
	}

	public List<Long> getConnectionIds() {
		return connectionIds;
	}

	public void setConnectionIds(List<Long> connectionIds) {
		this.connectionIds = connectionIds;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
