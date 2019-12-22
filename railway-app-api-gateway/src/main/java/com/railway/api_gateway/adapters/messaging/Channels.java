package com.railway.api_gateway.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String NOTIFY_EMERGENCY_SERVICES = "notify_emergency_services";
	static final String NOTIFY_INFRASTRUCTURE_DAMAGED = "notify_infrastructure_damaged";
	
	@Input(NOTIFY_EMERGENCY_SERVICES)
	SubscribableChannel notifyEmergencyServices();
	
	@Input(NOTIFY_INFRASTRUCTURE_DAMAGED)
	SubscribableChannel notifyInfrastructureDamaged();
}
