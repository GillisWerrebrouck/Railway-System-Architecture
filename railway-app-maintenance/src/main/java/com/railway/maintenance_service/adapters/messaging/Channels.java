package com.railway.maintenance_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String REQUEST_MAINTENANCE = "request_maintenance";
	static final String RESERVE_STAFF = "reserve_staff";
	static final String STAFF_RESERVED = "staff_reserved";
	
	@Input(REQUEST_MAINTENANCE)
	SubscribableChannel requestMaintenance();
	
	@Output(RESERVE_STAFF)
	MessageChannel reserveStaff();
	
	@Input(STAFF_RESERVED)
	SubscribableChannel staffReserved();
}
