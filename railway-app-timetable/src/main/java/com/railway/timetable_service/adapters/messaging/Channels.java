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
	static final String RESERVE_STAFF = "reserve_staff";
	static final String STAFF_RESERVED = "staff_reserved";
	static final String DISCARD_TRAIN_RESERVATION = "discard_train_reservation";
	static final String DISCARD_STATION_RESERVATIONS = "discard_station_reservations";
	static final String DISCARD_STAFF_RESERVATIONS = "discard_staff_reservations";
	static final String RESERVE_GROUP_SEATS = "reserve_group_seats";
	static final String GROUP_SEATS_RESERVED = "group_seats_reserved";
	static final String DISCARD_RESERVED_SEATS = "discard_reserved_seats";
	static final String CHECK_ROUTE_USAGE = "check_route_usage";
	static final String ROUTE_USAGE_CHECKED = "route_usage_checked";
	static final String NOTIFY_TRAIN_OUT_OF_SERVICE = "notify_train_out_of_service";

	@Output(GET_ROUTE)
	MessageChannel getRoute();
	
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
	
	@Output(RESERVE_STAFF)
	MessageChannel reserveStaff();
	
	@Input(STAFF_RESERVED)
	SubscribableChannel processStaffReservedResponse();
	
	@Output(DISCARD_TRAIN_RESERVATION)
	MessageChannel discardTrainReservation();
	
	@Output(DISCARD_STATION_RESERVATIONS)
	MessageChannel discardStationReservations();
	
	@Output(DISCARD_STAFF_RESERVATIONS)
	MessageChannel discardStaffReservations();

	@Input(RESERVE_GROUP_SEATS)
	SubscribableChannel reserveGroupSeats();

	@Output(GROUP_SEATS_RESERVED)
	MessageChannel groupSeatsReserved();

	@Input(DISCARD_RESERVED_SEATS)
	SubscribableChannel discardReservedSeats();
	
	@Input(CHECK_ROUTE_USAGE)
	SubscribableChannel getRouteUsage();
	
	@Output(ROUTE_USAGE_CHECKED)
	MessageChannel routeUsageChecked();
  
	@Input(NOTIFY_TRAIN_OUT_OF_SERVICE)
	SubscribableChannel processTrainOutOfService();
}
