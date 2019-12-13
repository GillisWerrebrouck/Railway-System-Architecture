package com.railway.staff_service.adapters.messaging;

import java.time.LocalDateTime;
import java.util.UUID;

import com.railway.staff_service.domain.StaffMemberType;

public class StaffRequest {
	private final UUID requestId;
	private final int amount;
	private final StaffMemberType staffMemberType;
	private final LocalDateTime startDate;
	private final LocalDateTime endDate;
	
	public StaffRequest(UUID requestId, int amount, StaffMemberType staffMemberType, LocalDateTime startDate, LocalDateTime endDate) {
		this.requestId = requestId;
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
