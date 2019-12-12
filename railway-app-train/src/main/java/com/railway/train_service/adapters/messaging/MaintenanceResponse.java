package com.railway.train_service.adapters.messaging;

public class MaintenanceResponse {
	private String responseMessage;
	
	public MaintenanceResponse(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	public String getResponseMessage() {
		return responseMessage;
	}
}
