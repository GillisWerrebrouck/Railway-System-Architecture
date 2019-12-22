package com.railway.train_service.adapters.messaging;

import java.time.LocalDateTime;

public class AccidentRequest {
	private Long timetableId;
	private String trainId;
	private String accidentMessage;
	private boolean emergencyServiceRequired;
	private LocalDateTime accidentDate;
	
	public AccidentRequest(Long timetableId, String trainId, String maintenanceMessage, boolean emergencyServiceRequired) {
		this.timetableId = timetableId;
		this.trainId = trainId;
		this.accidentMessage = maintenanceMessage;
		this.emergencyServiceRequired = emergencyServiceRequired;
		this.accidentDate= LocalDateTime.now();
	}
	
	public Long getTimetableId() {
		return timetableId;
	}
	
	public void setTimetableId(Long timetableId) {
		this.timetableId = timetableId;
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
