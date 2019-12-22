package com.railway.route_management_service.adapters.messaging;

public class StationRequest {
	private String id;
	private String name;
	
	public StationRequest(String id) {
		this.id = id;
	}
	
	public StationRequest(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
