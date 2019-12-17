package com.railway.ticket_sale_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

    static final String TICKET_CREATED = "ticket_created";
    static final String GET_ROUTE_DETAILS= "get_route_details";
    static final String ROUTE_DETAILS_FETCHED = "route_details_fetched";
    static final String RESERVE_GROUP_SEATS = "reserve_group_seats";
    static final String GROUP_SEATS_RESERVED = "group_seats_reserved";
    static final String DISCARD_RESERVED_SEATS = "discard_reserved_seats";

    @Output(TICKET_CREATED)
    MessageChannel ticketCreated();

    @Output(GET_ROUTE_DETAILS)
    MessageChannel getRouteDetails();

    @Input(ROUTE_DETAILS_FETCHED)
    SubscribableChannel routeDetailsFetched();

    @Output(RESERVE_GROUP_SEATS)
    MessageChannel reserveGroupSeats();

    @Input(GROUP_SEATS_RESERVED)
    SubscribableChannel groupSeatsReserved();

    @Output(DISCARD_RESERVED_SEATS)
    MessageChannel discardReservedSeats();

}
