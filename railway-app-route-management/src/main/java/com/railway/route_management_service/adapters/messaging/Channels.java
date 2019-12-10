package com.railway.route_management_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String GET_ROUTE = "get_route";
	static final String ROUTE_FETCHED = "route_fetched";
	static final String STATION_CREATED = "station_created";
	static final String STATION_DELETED = "station_deleted";
	
	@Input(GET_ROUTE)
	SubscribableChannel getRoute();
	
	@Output(ROUTE_FETCHED)
	MessageChannel routeFetched();

	@Input(STATION_CREATED)
	MessageChannel stationCreated();
	
	@Input(STATION_DELETED)
	MessageChannel stationDeleted();
}
