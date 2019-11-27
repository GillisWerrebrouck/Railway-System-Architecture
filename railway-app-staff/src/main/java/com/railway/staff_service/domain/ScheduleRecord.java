package com.railway.staff_service.domain;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ScheduleRecord {

	@Id
	private String id;
	private String scheduleRecordId;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean booked;
	private StaffMemberType staffMemberType;
	
	public ScheduleRecord() {
		this.startDate = null;
		this.endDate = null;
		this.staffMemberType = null;
	}
	
	@PersistenceConstructor
	public ScheduleRecord(LocalDate startDate, LocalDate endDate, StaffMemberType staffMemberType) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.staffMemberType = staffMemberType;
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
	
	public boolean isBooked() {
		return booked;
	}
	
	public void setBooked(boolean booked) {
		this.booked = booked;
	}
	
	public StaffMemberType getStaffMemberType() {
		return staffMemberType;
	}
	
	public void setStaffMemberType(StaffMemberType staffMemberType) {
		this.staffMemberType = staffMemberType;
	}
	
	public String getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "StaffSchedule [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", booked=" + booked
				+ ", staffMemberType=" + staffMemberType + "]";
	}

	public String getScheduleRecordId() {
		return scheduleRecordId;
	}

	public void setScheduleRecordId(String scheduleRecordId) {
		this.scheduleRecordId = scheduleRecordId;
	}
	
}