package com.railway.train_service.domain;

import java.time.LocalDateTime;

public class ScheduleItem {
	private Long timetableId;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private ReservationType reservationType;
	
	public ScheduleItem(Long timetableId, ReservationType reservationType, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		this.timetableId = timetableId;
		this.reservationType = reservationType;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}
	
	public Long getTimetableId() {
		return timetableId;
	}
	
	public void setTimetableId(Long timetableId) {
		this.timetableId = timetableId;
	}
	
	public ReservationType getReservationType() {
		return reservationType;
	}
	
	public void setReservationType(ReservationType reservationType) {
		this.reservationType = reservationType;
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
