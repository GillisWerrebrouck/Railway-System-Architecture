package com.railway.maintenance_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String REQUEST_MAINTENANCE = "request_maintenance";
	static final String RESERVE_STAFF = "reserve_staff";
	static final String STAFF_RESERVED = "staff_reserved";
	static final String NOTIFY_ACCIDENT = "notify_accident";
	static final String NOTIFY_INFRASTRUCTURE_DAMAGED = "notify_infrastructure_damaged";
	
	@Input(REQUEST_MAINTENANCE)
	SubscribableChannel requestMaintenance();
	
	@Input(NOTIFY_ACCIDENT)
	SubscribableChannel notifyAccident();
	
	@Output(RESERVE_STAFF)
	MessageChannel reserveStaff();
	
	@Input(STAFF_RESERVED)
	SubscribableChannel staffReserved();

	@Output(NOTIFY_INFRASTRUCTURE_DAMAGED)
	MessageChannel notifyInfrastructureDamaged();
}
