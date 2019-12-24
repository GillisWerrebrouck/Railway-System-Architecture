package com.railway.timetable_service.adapters.messaging;

public class UpdateDelayRequest {
	private Long TimetableId;
	private int delayInMinutes;
	
	public UpdateDelayRequest(Long timetableId, int delayInMinutes) {
		TimetableId = timetableId;
		this.delayInMinutes = delayInMinutes;
	}


	public Long getTimetableId() {
		return TimetableId;
	}


	public void setTimetableId(Long timetableId) {
		TimetableId = timetableId;
	}


	public int getDelayInMinutes() {
		return delayInMinutes;
	}


	public void setDelayInMinutes(int delayInMinutes) {
		this.delayInMinutes = delayInMinutes;
	}
}
