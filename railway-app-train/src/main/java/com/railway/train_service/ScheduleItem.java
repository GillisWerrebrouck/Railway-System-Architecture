package com.railway.train_service;

import java.time.LocalDate;

public class ScheduleItem {
	private LocalDate startDateTime;
	private LocalDate endDateTime;
	
	public ScheduleItem(LocalDate startDateTime, LocalDate endDateTime) {
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}
	
	public LocalDate getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDate startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDate getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDate endDateTime) {
		this.endDateTime = endDateTime;
	}
}
