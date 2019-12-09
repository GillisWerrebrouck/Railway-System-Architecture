package com.railway.station_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String RESERVE_STATIONS = "reserve_stations";
	static final String STATIONS_RESERVED = "stations_reserved";
	
	@Input(RESERVE_STATIONS)
	SubscribableChannel reserveStations();
	
	@Output(STATIONS_RESERVED)
	MessageChannel stationsReserved();
}
