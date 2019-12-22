package com.railway.staff_service.adapters.messaging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class StaffResponse {
	private UUID requestId;
	private Collection<String> staffIds;
	private String responseMessage;
	
	public StaffResponse(UUID requestId) {
		this.requestId = requestId;
		this.staffIds = new ArrayList<>();
		this.responseMessage = null;
	}
	
	public UUID getRequestId() {
		return requestId;
	}
	
	public Collection<String> getStaffIds() {
		return staffIds;
	}
	
	public void addStaffId(String staffId) {
		this.staffIds.add(staffId);
	}
	
	public void setStaffIds(Collection<String> staffIds) {
		this.staffIds = staffIds;
	}
	
	public String getResponseMessage() {
		return responseMessage;
	}
	
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
}
