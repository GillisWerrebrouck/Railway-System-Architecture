package com.railway.train_service.domain;

import java.time.LocalDateTime;

public class ScheduleItem {
	private Long timetableId;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	
	public ScheduleItem(Long timetableId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		this.timetableId = timetableId;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}
	
	public Long scheduleItem() {
		return timetableId;
	}
	
	public Long getTimetableId() {
		return timetableId;
	}
	
	public void setTimetableId(Long timetableId) {
		this.timetableId = timetableId;
	}
	
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}
}
