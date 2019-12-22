package com.railway.timetable_service.adapters.messaging;

import java.time.LocalDateTime;
import java.util.UUID;

public class StaffRequest {
	private final UUID requestId;
	private final int amount;
	private final StaffMemberType staffMemberType;
	private final LocalDateTime startDate;
	private final LocalDateTime endDate;
	
	public StaffRequest(int amount, StaffMemberType staffMemberType, LocalDateTime startDate, LocalDateTime endDate) {
		this.requestId = UUID.randomUUID();
		this.amount = amount;
		this.staffMemberType = staffMemberType;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public UUID getRequestId() {
		return requestId;
	}

	public int getAmount() {
		return amount;
	}

	public StaffMemberType getStaffMemberType() {
		return staffMemberType;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}
}
