package com.railway.station_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String RESERVE_STATIONS = "reserve_stations";
	static final String STATIONS_RESERVED = "stations_reserved";
	static final String DISCARD_STATION_RESERVATIONS = "discard_station_reservations";
	static final String STATION_CREATED = "station_created";
	static final String STATION_DELETED = "station_deleted";
	
	@Input(RESERVE_STATIONS)
	SubscribableChannel reserveStations();
	
	@Input(DISCARD_STATION_RESERVATIONS)
	SubscribableChannel discardStationReservations();
	
	@Output(STATIONS_RESERVED)
	MessageChannel stationsReserved();
	
	@Output(STATION_CREATED)
	MessageChannel stationCreated();
	
	@Output(STATION_DELETED)
	MessageChannel stationDeleted();
}
