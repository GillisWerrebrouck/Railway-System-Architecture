package com.railway.ticket_sale_service.adapters.messaging;

import com.railway.ticket_sale_service.domain.Ticket;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MessageGateway {

    @Gateway(requestChannel = Channels.TICKET_CREATED)
    public void ticketCreated(Ticket ticket);

    @Gateway(requestChannel = Channels.GET_ROUTE_DETAILS)
    public void getRouteDetails(RouteDetailRequest routeDetailRequest);

    @Gateway(requestChannel = Channels.RESERVE_GROUP_SEATS)
    public void reserveGroupSeats(GroupSeatsRequest groupSeatsRequest);

    @Gateway(requestChannel = Channels.DISCARD_RESERVED_SEATS)
    public void discardGroupSeats(GroupSeatsRequest groupSeatsRequest);
}
