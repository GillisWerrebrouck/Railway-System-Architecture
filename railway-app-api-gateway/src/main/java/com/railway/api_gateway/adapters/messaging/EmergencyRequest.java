package com.railway.api_gateway.adapters.messaging;

public class EmergencyRequest {


	private String trainId;
	private String message;
	
	public EmergencyRequest(String trainId, String message) {
		this.trainId = trainId;
		this.message = message;
	}

	public String getTrainId() {
		return trainId;
	}

	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
