package com.railway.train_service.adapters.messaging;

import java.time.LocalDateTime;

public class AccidentRequest {
	private String trainId;
	private String accidentMessage;
	private boolean emergencyServiceRequired;
	private LocalDateTime accidentDate;
	
	public AccidentRequest(String trainId, String maintenanceMessage, boolean emergencyServiceRequired) {
		this.trainId = trainId;
		this.accidentMessage = maintenanceMessage;
		this.emergencyServiceRequired = emergencyServiceRequired;
		this.accidentDate= LocalDateTime.now();
	}
	
	public String getTrainId() {
		return trainId;
	}
	
	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}

	public String getAccidentMessage() {
		return accidentMessage;
	}

	public void setAccidentMessage(String accidentMessage) {
		this.accidentMessage = accidentMessage;
	}

	public boolean isEmergencyServiceRequired() {
		return emergencyServiceRequired;
	}

	public void setEmergencyServiceRequired(boolean emergencyServiceRequired) {
		this.emergencyServiceRequired = emergencyServiceRequired;
	}

	public LocalDateTime getAccidentDate() {
		return accidentDate;
	}

	public void setAccidentDate(LocalDateTime accidentDate) {
		this.accidentDate = accidentDate;
	}
}
