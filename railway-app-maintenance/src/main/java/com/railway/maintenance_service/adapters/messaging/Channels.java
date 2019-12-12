package com.railway.maintenance_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String REQUEST_MAINTENANCE = "request_maintenance";
	
	@Input(REQUEST_MAINTENANCE)
	SubscribableChannel requestMaintenance();
}
