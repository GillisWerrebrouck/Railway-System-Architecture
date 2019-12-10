package com.railway.station_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String RESERVE_STATIONS = "reserve_stations";
	static final String STATIONS_RESERVED = "stations_reserved";
	static final String DISCARD_STATION_RESERVATIONS = "discard_station_reservations";
	
	@Input(RESERVE_STATIONS)
	SubscribableChannel reserveStations();
	
	@Input(DISCARD_STATION_RESERVATIONS)
	SubscribableChannel discardStationReservations();
	
	@Output(STATIONS_RESERVED)
	MessageChannel stationsReserved();
}
