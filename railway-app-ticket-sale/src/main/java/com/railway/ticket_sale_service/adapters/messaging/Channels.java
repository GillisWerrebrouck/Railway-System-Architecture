package com.railway.ticket_sale_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

    static final String TICKET_CREATED = "ticket_created";
    static final String GET_ROUTE_DETAILS= "get_route_details";
    static final String ROUTE_DETAILS_FETCHED = "route_details_fetched";

    @Output(TICKET_CREATED)
    MessageChannel ticketCreated();

    @Output(GET_ROUTE_DETAILS)
    MessageChannel getRouteDetails();

    @Input(ROUTE_DETAILS_FETCHED)
    SubscribableChannel routeDetailsFetched();
}
