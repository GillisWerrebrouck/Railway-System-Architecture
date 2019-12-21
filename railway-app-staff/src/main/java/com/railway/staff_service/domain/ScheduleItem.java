package com.railway.staff_service.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;

public class ScheduleItem {
	@Id
	private UUID id;
	private UUID requestId;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	public ScheduleItem(UUID requestId, LocalDateTime startDate, LocalDateTime endDate) {
		this.id = UUID.randomUUID();
		this.requestId = requestId;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public UUID getId() {
		return id;
	}
	
	public UUID getRequestId() {
		return requestId;
	}
	
	public void setRequestId(UUID requestId) {
		this.requestId = requestId;
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
	
	@Override
	public String toString() {
		return "StaffSchedule [id=" + id.toString() + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
}
