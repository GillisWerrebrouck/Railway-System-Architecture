package com.railway.train_service.adapters.messaging;

import java.time.LocalDateTime;

public class MaintenanceRequest {
	private String trainId;
	private String maintenanceMessage;
	private LocalDateTime maintenanceDate;
	
	public MaintenanceRequest(String trainId, String maintenanceMessage, LocalDateTime maintenanceDate) {
		this.trainId = trainId;
		this.maintenanceMessage = maintenanceMessage;
		this.maintenanceDate = maintenanceDate;
	}
	
	public String getTrainId() {
		return trainId;
	}
	
	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}
	
	public String getMaintenanceMessage() {
		return maintenanceMessage;
	}
	
	public void setMaintenanceMessage(String maintenanceMessage) {
		this.maintenanceMessage = maintenanceMessage;
	}
	
	public LocalDateTime getMaintenanceDate() {
		return maintenanceDate;
	}
	
	public void setMaintenanceDate(LocalDateTime maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}
}
