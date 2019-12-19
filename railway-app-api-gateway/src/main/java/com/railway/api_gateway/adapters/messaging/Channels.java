package com.railway.api_gateway.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String NOTIFY_EMERGENCY_SERVICES = "notify_emergency_services";
	
	@Output(NOTIFY_EMERGENCY_SERVICES)
	MessageChannel notifyEmergencyServices();
}
