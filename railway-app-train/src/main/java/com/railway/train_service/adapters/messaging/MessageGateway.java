package com.railway.train_service.adapters.messaging;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MessageGateway {
	@Gateway(requestChannel = Channels.REQUEST_MAINTENANCE)
	public void requestMaintenance(MaintenanceRequest request);
}
