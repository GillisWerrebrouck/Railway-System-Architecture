package com.railway.maintenance_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String REQUEST_MAINTENANCE = "request_maintenance";
	static final String RESERVE_STAFF_M = "reserve_staff_m";
	static final String STAFF_RESERVED_M = "staff_reserved_m";
	static final String CHANGE_TRAIN_STATUS = "change_train_status";
	static final String NOTIFY_ACCIDENT = "notify_accident";
	static final String NOTIFY_INFRASTRUCTURE_DAMAGE = "notify_infrastructure_damage";
  
	@Input(REQUEST_MAINTENANCE)
	SubscribableChannel requestMaintenance();
	
	@Input(NOTIFY_ACCIDENT)
	SubscribableChannel notifyAccident();
	
	@Output(RESERVE_STAFF_M)
	MessageChannel reserveStaff();
	
	@Input(STAFF_RESERVED_M)
	SubscribableChannel staffReserved();
  
	@Output(NOTIFY_INFRASTRUCTURE_DAMAGE)
	MessageChannel notifyInfrastructureDamage();
  
	@Output(CHANGE_TRAIN_STATUS)
	MessageChannel changeTrainStatus();
}
