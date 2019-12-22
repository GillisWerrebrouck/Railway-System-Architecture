package com.railway.maintenance_service.adapters.messaging;

import java.time.LocalDateTime;

public class AccidentRequest {
	private String trainId;
	private String accidentMessage;
	private LocalDateTime accidentDate;
	
	public AccidentRequest(String trainId, String maintenanceMessage, LocalDateTime accidentDate) {
		this.trainId = trainId;
		this.accidentMessage = maintenanceMessage;
		this.accidentDate = accidentDate;
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

	public LocalDateTime getAccidentDate() {
		return accidentDate;
	}

	public void setAccidentDate(LocalDateTime accidentDate) {
		this.accidentDate = accidentDate;
	}
}
