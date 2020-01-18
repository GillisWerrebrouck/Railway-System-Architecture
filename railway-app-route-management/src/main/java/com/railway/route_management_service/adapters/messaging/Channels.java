package com.railway.route_management_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String GET_ROUTE = "get_route";
	static final String ROUTE_FETCHED = "route_fetched";
	static final String GET_ROUTE_D = "get_route_d";
	static final String ROUTE_FETCHED_D = "route_fetched_d";
	static final String STATION_CREATED = "station_created";
	static final String STATION_DELETED = "station_deleted";
	static final String GET_ROUTE_DETAILS= "get_route_details";
	static final String ROUTE_DETAILS_FETCHED = "route_details_fetched";
	static final String CHECK_ROUTE_USAGE = "check_route_usage";
	static final String ROUTE_USAGE_CHECKED = "route_usage_checked";

	@Input(GET_ROUTE)
	SubscribableChannel getRoute();
	
	@Output(ROUTE_FETCHED)
	MessageChannel routeFetched();

	@Input(GET_ROUTE_D)
	SubscribableChannel getRouteForDelay();
	
	@Output(ROUTE_FETCHED_D)
	MessageChannel routeFetchedForDelay();

	@Input(STATION_CREATED)
	MessageChannel stationCreated();
	
	@Input(STATION_DELETED)
	MessageChannel stationDeleted();

	@Input(GET_ROUTE_DETAILS)
	SubscribableChannel getRouteDetails();

	@Output(ROUTE_DETAILS_FETCHED)
	SubscribableChannel routeDetailsFetched();
	
	@Output(CHECK_ROUTE_USAGE)
	MessageChannel getRouteUsage();
	
	@Input(ROUTE_USAGE_CHECKED)
	SubscribableChannel getRouteUsageChecked();

}
