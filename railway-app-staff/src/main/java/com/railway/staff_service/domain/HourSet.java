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
	
	public boolean isInBetween(LocalDate startDate, LocalDate endDate) {
		if(startDate.isBefore(endDate)) {
			return (startDate.isAfter(this.startDate) || startDate.equals(this.startDate)) && 
					(endDate.isBefore(this.endDate) || endDate.equals(this.endDate));
		}
		return false;
	}
	
	public boolean isInBetween(HourSet hourSet) {
		return (hourSet.startDate.isAfter(this.startDate) || hourSet.startDate.equals(this.startDate)) && 
				(hourSet.endDate.isBefore(this.endDate) || hourSet.endDate.equals(this.endDate));
	}
	
	public boolean setWorking(HourSet hourset) {
		if(isInBetween(hourset)) {
			this.available=false;
			return true;
		}
		return false;
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
