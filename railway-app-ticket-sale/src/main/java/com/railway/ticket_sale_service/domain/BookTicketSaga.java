package com.railway.ticket_sale_service.domain;

import com.railway.ticket_sale_service.adapters.messaging.MessageGateway;
import com.railway.ticket_sale_service.adapters.messaging.GroupSeatsRequest;
import com.railway.ticket_sale_service.adapters.messaging.RouteDetailRequest;
import com.railway.ticket_sale_service.adapters.messaging.RouteDetailResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.RouteMatcher;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookTicketSaga {

    private static Logger logger = LoggerFactory.getLogger(BookTicketSaga.class);

    private final MessageGateway gateway;
    private final Set<BookTicketListener> listeners;

    @Autowired
    public BookTicketSaga(MessageGateway gateway) {
        this.gateway = gateway;
        this.listeners = new HashSet<>(5);
    }

    public void addListener(BookTicketListener listener){
        listeners.add(listener);
    }

    public void startBookTicketSaga(List<Ticket> tickets, UUID startStation, UUID endStation){
        logger.info("[Book Ticket Saga] saga started. Fetching route details.");

        Long[] ticketIds = tickets.stream().map(ticket -> ticket.getId()).toArray(Long[]::new);
        RouteDetailRequest routeDetailRequest = new RouteDetailRequest(ticketIds, startStation, endStation);
        tickets.forEach(t -> t.setRouteDetailsRequestId(routeDetailRequest.getRouteDetailRequestId()));

        gateway.getRouteDetails(routeDetailRequest);
    }

    public void startBookGroupTicketSaga(Ticket ticket, UUID startStation, UUID endStation){
        logger.info("[Book Group Ticket Saga] saga started. Fetching route details.");

        RouteDetailRequest routeDetailRequest = new RouteDetailRequest(ticket.getId(), startStation, endStation);
        ticket.setRouteDetailsRequestId(routeDetailRequest.getRouteDetailRequestId());
        gateway.getRouteDetails(routeDetailRequest);

        GroupSeatsRequest reserveRequest = new GroupSeatsRequest(ticket.getId(), ticket.getTimetableId(), ticket.getAmountOfSeats());
        ticket.setReserveGroupSeatsRequestId(reserveRequest.getGroupSeatsRequestId());
        gateway.reserveGroupSeats(reserveRequest);
    }

    public void onRouteDetailsFetched(List<Ticket> tickets, String startStation, String endStation){
        tickets.stream()
                .filter(ticket -> ticket.getStatus() != TicketStatus.RESERVE_GROUP_SEATS_FAILED)
                .forEach(ticket -> {
                    ticket.setStatus(ticket.getStatus() == TicketStatus.GROUP_SEATS_RESERVED ? TicketStatus.DETAILS_FETCHED_GROUP_SEATS_RESERVED : TicketStatus.DETAILS_FETCHED);
                    ticket.setStartStation(startStation);
                    ticket.setEndStation(endStation);
                });
        checkTicketCompletion(tickets);
    }


    public void onReservedGroupSeats(Ticket ticket){
        if(ticket.getStatus() == TicketStatus.FETCHING_DETAILS_FAILED){
            gateway.discardGroupSeats(new GroupSeatsRequest(ticket.getId(), ticket.getTimetableId(), ticket.getAmountOfSeats()));
        }else{
            TicketStatus status = ticket.getStatus() == TicketStatus.DETAILS_FETCHED ? TicketStatus.DETAILS_FETCHED_GROUP_SEATS_RESERVED : TicketStatus.GROUP_SEATS_RESERVED;
            ticket.setStatus(status);
            checkTicketCompletion(Arrays.asList(ticket));
        }
    }

    public void onReserveGroupSeatsFailed(Ticket ticket){
        ticket.setStatus(TicketStatus.RESERVE_GROUP_SEATS_FAILED);
        notifyErrorListeners(Arrays.asList(ticket), "Could not reserve group seats.");
    }

    public void onRouteDetailsFetchingFailed(List<Ticket> tickets){
        tickets.stream()
                .filter(ticket -> ticket.getStatus() == TicketStatus.GROUP_SEATS_RESERVED)
                .forEach(ticket -> gateway.discardGroupSeats(new GroupSeatsRequest(ticket.getId(), ticket.getTimetableId(), ticket.getAmountOfSeats())));
        notifyErrorListeners(tickets,"Could not get route details.");
    }

    public void onGroupTicketAmountOfSeatsToLow(Ticket ticket){
        notifyErrorListeners(Arrays.asList(ticket),"Too few seats for groupticket.");
    }

    private void checkTicketCompletion(List<Ticket> tickets){
        List<Ticket> bookedTickets = tickets.stream()
                .filter(ticket -> (ticket.getType() == TicketType.SINGLE && ticket.getStatus() == TicketStatus.DETAILS_FETCHED) || ticket.getStatus() ==  TicketStatus.DETAILS_FETCHED_GROUP_SEATS_RESERVED)
                .map(ticket -> {
                    ticket.setStatus(TicketStatus.BOOKED);
                    gateway.ticketCreated(ticket);
                    return ticket;
                })
                .collect(Collectors.toList());

        if(!bookedTickets.isEmpty())
            notifyListeners(bookedTickets);
    }

    private void notifyListeners(List<Ticket> tickets){
        if(!tickets.isEmpty())
            this.listeners.forEach(l -> l.onBookTicketResult(tickets));
    }

    private void notifyErrorListeners(List<Ticket> tickets, String errorMessage){
        this.listeners.forEach(l -> l.onBookTicketError(tickets, errorMessage));
    }

}
