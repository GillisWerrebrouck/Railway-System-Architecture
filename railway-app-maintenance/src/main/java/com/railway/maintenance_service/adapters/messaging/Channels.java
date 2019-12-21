package com.railway.maintenance_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String REQUEST_MAINTENANCE = "request_maintenance";
	static final String RESERVE_STAFF = "reserve_staff";
	static final String STAFF_RESERVED = "staff_reserved";
	static final String CHANGE_TRAIN_STATUS = "change_train_status";
	static final String NOTIFY_ACCIDENT = "notify_accident";

	@Input(REQUEST_MAINTENANCE)
	SubscribableChannel requestMaintenance();
	
	@Input(NOTIFY_ACCIDENT)
	SubscribableChannel notifyAccident();
	
	@Output(RESERVE_STAFF)
	MessageChannel reserveStaff();
	
	@Input(STAFF_RESERVED)
	SubscribableChannel staffReserved();
	
	@Output(CHANGE_TRAIN_STATUS)
	MessageChannel changeTrainStatus();
}
