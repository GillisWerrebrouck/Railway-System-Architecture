package com.railway.timetable_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String GET_ROUTE = "get_route";
	static final String ROUTE_FETCHED = "route_fetched";
	static final String TIMETABLE_ITEM_CREATED_RESULT = "timetable_item_created_result";
	
	@Output(GET_ROUTE)
	MessageChannel getRoute();
	
	@Input(ROUTE_FETCHED)
	SubscribableChannel processRouteResponse();
	
	@Output(TIMETABLE_ITEM_CREATED_RESULT)
	MessageChannel checkInResult();
}
