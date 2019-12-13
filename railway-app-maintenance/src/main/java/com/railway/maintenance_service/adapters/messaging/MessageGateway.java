package com.railway.maintenance_service.adapters.messaging;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MessageGateway {
	@Gateway(requestChannel = Channels.RESERVE_STAFF)
	public void reserveStaff(StaffRequest request);
}
