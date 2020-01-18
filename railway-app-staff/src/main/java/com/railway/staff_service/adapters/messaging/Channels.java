package com.railway.staff_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String RESERVE_STAFF = "reserve_staff";
	static final String RESERVE_STAFF_M = "reserve_staff_m";
	static final String STAFF_RESERVED = "staff_reserved";
	static final String STAFF_RESERVED_M = "staff_reserved_m";
	static final String DISCARD_STAFF_RESERVATIONS = "discard_staff_reservations";

	@Input(RESERVE_STAFF)
	SubscribableChannel reserveStaff();
	
	@Input(RESERVE_STAFF_M)
	SubscribableChannel reserveStaffMaintenance();
	
	@Output(STAFF_RESERVED)
	MessageChannel staffReserved();
	
	@Output(STAFF_RESERVED_M)
	MessageChannel staffReservedMaintenance();
	
	@Input(DISCARD_STAFF_RESERVATIONS)
	SubscribableChannel discardStaffReservations();
}
