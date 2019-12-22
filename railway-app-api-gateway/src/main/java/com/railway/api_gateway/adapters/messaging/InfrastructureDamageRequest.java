package com.railway.api_gateway.adapters.messaging;

public class InfrastructureDamageRequest {
	private String location;
	private String message;
	
	public InfrastructureDamageRequest(String location, String message) {
		this.location = location;
		this.message = message;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
