package com.railway.train_service.adapters.messaging;


public class AccidentRequest {
	private String trainId;
	private String accidentMessage;
	private boolean emergencyServiceRequired;
	
	public AccidentRequest(String trainId, String maintenanceMessage, boolean emergencyServiceRequired) {
		this.trainId = trainId;
		this.accidentMessage = maintenanceMessage;
		this.emergencyServiceRequired = emergencyServiceRequired;
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
	
	

}
