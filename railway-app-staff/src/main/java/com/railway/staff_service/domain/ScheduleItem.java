package com.railway.staff_service.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ScheduleItem {
	@Id
	private String id;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private StaffMember staffMember;
	
	public ScheduleItem() {}
	
	@PersistenceConstructor
	public ScheduleItem(LocalDateTime startDate, LocalDateTime endDate, StaffMember staffMember) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.staffMember = staffMember;
	}
	
	public String getId() {
		return id;
	}
	
	public LocalDateTime getStartDate() {
		return startDate;
	}
	
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	
	public LocalDateTime getEndDate() {
		return endDate;
	}
	
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
	public StaffMember getStaffMember() {
		return staffMember;
	}
	
	public void setStaffMember(StaffMember staffMember) {
		this.staffMember = staffMember;
	}
	
	@Override
	public String toString() {
		return "StaffSchedule [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", staffMember=" + staffMember.toString() + "]";
	}
}
