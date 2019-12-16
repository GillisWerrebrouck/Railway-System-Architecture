package com.railway.ticket_sale_service.domain;

import com.railway.ticket_sale_service.adapters.messaging.MessageGateway;
import com.railway.ticket_sale_service.adapters.messaging.ReserveGroupSeatsRequest;
import com.railway.ticket_sale_service.adapters.messaging.RouteDetailRequest;
import com.railway.ticket_sale_service.adapters.messaging.RouteDetailResponse;
import com.railway.ticket_sale_service.adapters.rest.TicketRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    public void startBuyTicketSaga(Ticket ticket, UUID startStation, UUID endStation, Long timeTableId, int amountOfSeats){
        logger.info("[Book Ticket Saga] saga started. Fetching route details.");
        RouteDetailRequest routeDetailRequest = new RouteDetailRequest(ticket.getId(), startStation, endStation);
        ticket.setRouteDetailsRequestId(routeDetailRequest.getRouteDetailRequestId());
        gateway.getRouteDetails(routeDetailRequest);
        if(ticket.getType() == TicketType.GROUP){
            ReserveGroupSeatsRequest reserveRequest = new ReserveGroupSeatsRequest(ticket.getId(), timeTableId, amountOfSeats);
            ticket.setReserveGroupSeatsRequestId(reserveRequest.getReserveGroupSeatsRequestId());
            gateway.reserveGroupSeats(reserveRequest);
        }
    }

    public void onRouteDetailsFetched(Ticket ticket, double price, String startStation, String endStation){
        TicketStatus status = ticket.getStatus() == TicketStatus.GROUP_SEATS_RESERVED ? TicketStatus.DETAILS_FETCHED_GROUP_SEATS_RESERVED : TicketStatus.DETAILS_FETCHED;
        ticket.setPrice(price);
        ticket.setStartStation(startStation);
        ticket.setEndStation(endStation);
        ticket.setStatus(status);
        checkTicketCompletion(ticket);
    }

    public void onReservedGroupSeats(Ticket ticket){
        TicketStatus status = ticket.getStatus() == TicketStatus.DETAILS_FETCHED ? TicketStatus.DETAILS_FETCHED_GROUP_SEATS_RESERVED : TicketStatus.GROUP_SEATS_RESERVED;
        ticket.setStatus(status);
        checkTicketCompletion(ticket);
    }

    public void onRouteDetailsFetchingFailed(Ticket ticket){
        ticket.setStatus(TicketStatus.FETCHING_DETAILS_FAILED);
        notifyListeners(ticket);
    }

    public void onReserveGroupSeatsFailed(Ticket ticket){
        ticket.setStatus(TicketStatus.RESERVE_GROUP_SEATS_FAILED);
        notifyListeners(ticket);
    }
    private void checkTicketCompletion(Ticket ticket){
        if(ticket.getType() == TicketType.SINGLE){
            if(ticket.getStatus() == TicketStatus.DETAILS_FETCHED){
                ticket.setStatus(TicketStatus.BOOKED);
                gateway.ticketCreated(ticket);
                notifyListeners(ticket);
            }
        }else if(ticket.getStatus() ==  TicketStatus.DETAILS_FETCHED_GROUP_SEATS_RESERVED){
            ticket.setStatus(TicketStatus.BOOKED);
            gateway.ticketCreated(ticket);
            notifyListeners(ticket);
        }
    }

    private void notifyListeners(Ticket ticket){
        this.listeners.forEach(l -> l.onBookTicketListener(ticket));
    }

}