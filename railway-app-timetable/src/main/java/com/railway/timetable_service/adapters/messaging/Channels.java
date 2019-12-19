package com.railway.timetable_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String GET_ROUTE = "get_route";
	static final String ROUTE_FETCHED = "route_fetched";
	static final String RESERVE_STATIONS = "reserve_stations";
	static final String STATIONS_RESERVED = "stations_reserved";
	static final String RESERVE_TRAIN = "reserve_train";
	static final String TRAIN_RESERVED = "train_reserved";
	static final String DISCARD_TRAIN_RESERVATION = "discard_train_reservation";
	static final String DISCARD_STATION_RESERVATIONS = "discard_station_reservations";
	static final String NOTIFY_EXTRA_DELAY = "notify_extra_delay";
	static final String NOTIFY_DELAY = "notify_delay_t";
	
	@Output(GET_ROUTE)
	MessageChannel getRoute();
	
	@Input(NOTIFY_DELAY)
	SubscribableChannel notifyDelay();
	
	@Input(NOTIFY_EXTRA_DELAY)
	SubscribableChannel extraDelay();
	
	@Input(ROUTE_FETCHED)
	SubscribableChannel processRouteResponse();
	
	@Output(RESERVE_STATIONS)
	MessageChannel reserveStations();
	
	@Input(STATIONS_RESERVED)
	SubscribableChannel processStationsReservedResponse();
	
	@Output(RESERVE_TRAIN)
	MessageChannel reserveTrain();
	
	@Input(TRAIN_RESERVED)
	SubscribableChannel processTrainReservedResponse();
	
	@Output(DISCARD_TRAIN_RESERVATION)
	MessageChannel discardTrainReservation();
	
	@Output(DISCARD_STATION_RESERVATIONS)
	MessageChannel discardStationReservations();
}
