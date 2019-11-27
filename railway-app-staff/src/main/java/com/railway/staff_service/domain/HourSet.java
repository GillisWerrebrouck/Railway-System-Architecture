package com.railway.staff_service.domain;

import java.time.LocalDate;

public class HourSet {
	
	private boolean available;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public HourSet() {
		this.available=false;
		this.startDate=null;
		this.endDate=null;
	}
	
	public HourSet(LocalDate startDate, LocalDate endDate) throws HourSetException {
		if(startDate.isBefore(endDate)) {
			this.startDate=startDate;
			this.endDate=endDate;
			this.available=true;
		} else {
			throw new HourSetException(startDate,endDate);
		}
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "HourSet [startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
	
}
