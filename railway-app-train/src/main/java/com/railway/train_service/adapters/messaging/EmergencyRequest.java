package com.railway.train_service.adapters.messaging;

public class EmergencyRequest {
	private Long timetableId;
	private String message;
	
	public EmergencyRequest(Long timetableId, String message) {
		this.timetableId = timetableId;
		this.message = message;
	}

	public Long getTimetableId() {
		return timetableId;
	}
	
	public void setTimetableId(Long timetableId) {
		this.timetableId = timetableId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
