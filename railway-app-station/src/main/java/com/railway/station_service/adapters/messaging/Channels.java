package com.railway.station_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String RESERVE_STATION = "reserve_station";
	
	@Input(RESERVE_STATION)
	SubscribableChannel reserveStation();
}
