package com.railway.ticket_sale_service.domain;

import com.railway.ticket_sale_service.adapters.messaging.MessageGateway;
import com.railway.ticket_sale_service.adapters.messaging.RouteDetailRequest;
import com.railway.ticket_sale_service.adapters.messaging.RouteDetailResponse;
import com.railway.ticket_sale_service.adapters.rest.TicketRequest;
import com.railway.ticket_sale_service.adapters.rest.TicketRestController;
import com.railway.ticket_sale_service.persistence.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class TicketService {
    private static Logger logger = LoggerFactory.getLogger(TicketService.class);
    private final double PRICE_PER_KM = 0.165;

    private final TicketRepository ticketRepository;
    private final MessageGateway gateway;
    private final Set<BookTicketListener> listeners;


    @Autowired
    public TicketService(TicketRepository ticketRepository, MessageGateway messageGateway) {
        this.ticketRepository = ticketRepository;
        this.gateway = messageGateway;
        this.listeners = new HashSet<>(5);
    }

    public void registerListener(BookTicketListener listener){
        listeners.add(listener);
    }

    public synchronized void fetchRouteDetails(Ticket ticket, TicketRequest ticketRequest){
        logger.info("[Ticket Sale Service] fetch route details");
        RouteDetailRequest request = new RouteDetailRequest(ticketRequest.getRouteId(),
                UUID.fromString(ticketRequest.getStartStationId()), UUID.fromString(ticketRequest.getEndStationId()),
                ticket.getId());
        ticket.setRouteDetailsRequestId(request.getRouteDetailRequestId());
        ticketRepository.save(ticket);
        gateway.getRouteDetails(request);
    }

    public synchronized void bookTicket(RouteDetailResponse response){
        logger.info("[Ticket Sale Service] book ticket");
        Ticket ticket = ticketRepository.findById(response.getTicketId()).orElse(null);
        if(ticket != null && ticket.getRouteDetailsRequestId().compareTo(response.getRouteDetailRequestId()) == 0){
            ticket.setPrice(response.getDistance() * PRICE_PER_KM);
            ticket.setStartStation(response.getStartStationName());
            ticket.setEndStation(response.getStartStationName());
            ticketRepository.save(ticket);
            gateway.ticketCreated(ticket);
            this.listeners.forEach(l -> l.onBookTicketListener(ticket));
        }
    }
}
