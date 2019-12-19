package com.railway.api_gateway.adapters.messaging;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MessageGateway {
	@Gateway(requestChannel = Channels.NOTIFY_EMERGENCY_SERVICES)
	public void notifyEmergencyServices(EmergencyRequest emergencyRequest);

}
