package com.railway.api_gateway.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String NOTIFY_EMERGENCY_SERVICES = "notify_emergency_services";
	static final String NOTIFY_INFRASTRUCTURE_DAMAGE = "notify_infrastructure_damage";
	
	@Input(NOTIFY_EMERGENCY_SERVICES)
	SubscribableChannel notifyEmergencyServices();
	
	@Input(NOTIFY_INFRASTRUCTURE_DAMAGE)
	SubscribableChannel notifyInfrastructureDamage();
}
