package com.railway.maintenance_service.adapters.messaging;

public class InfrastructureDamagedRequest {
	private String trainId;
	private String message;
	
	public InfrastructureDamagedRequest(String trainId, String message) {
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
