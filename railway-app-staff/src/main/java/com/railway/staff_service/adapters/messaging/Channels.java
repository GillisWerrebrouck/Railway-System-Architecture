package com.railway.staff_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String RESERVE_STAFF = "reserve_staff";
	static final String STAFF_RESERVED = "staff_reserved";
	
	@Input(RESERVE_STAFF)
	SubscribableChannel reserveStaff();
	
	@Output(STAFF_RESERVED)
	MessageChannel staffReserved();
}
