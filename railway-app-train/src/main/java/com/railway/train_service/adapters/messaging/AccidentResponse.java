package com.railway.train_service.adapters.messaging;

public class AccidentResponse {
	private String responseMessage;
	
	public AccidentResponse(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	public String getResponseMessage() {
		return responseMessage;
	}
}
