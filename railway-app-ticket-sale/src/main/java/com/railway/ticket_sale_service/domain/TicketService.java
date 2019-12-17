package com.railway.ticket_sale_service.domain;

import com.railway.ticket_sale_service.adapters.messaging.MessageGateway;
import com.railway.ticket_sale_service.adapters.messaging.ReserveGroupSeatsResponse;
import com.railway.ticket_sale_service.adapters.messaging.RouteDetailResponse;
import com.railway.ticket_sale_service.adapters.rest.TicketRequest;
import com.railway.ticket_sale_service.persistence.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TicketService {
    private static Logger logger = LoggerFactory.getLogger(TicketService.class);
    private final double PRICE_PER_KM = 0.1056;
    private final double MAX_PRICE = 9.60;

    private final TicketRepository ticketRepository;
    private final BookTicketSaga bookTicketSaga;
    private final MessageGateway gateway;


    @Autowired
    public TicketService(TicketRepository ticketRepository, MessageGateway messageGateway, BookTicketSaga bookTicketSaga) {
        this.ticketRepository = ticketRepository;
        this.bookTicketSaga = bookTicketSaga;
        this.gateway = messageGateway;
    }

    public void registerListener(BookTicketListener listener){
        this.bookTicketSaga.addListener(listener);
    }

    public synchronized void buyTicket(Ticket ticket, TicketRequest ticketRequest){
        this.bookTicketSaga.startBuyTicketSaga(ticket, UUID.fromString(ticketRequest.getEndStationId()),
                UUID.fromString(ticketRequest.getStartStationId()));
        this.ticketRepository.save(ticket);
    }

    public synchronized void routeDetailsFetched(RouteDetailResponse response){
        final Ticket ticket = ticketRepository.findById(response.getTicketId()).orElse(null);
        if(ticket != null && ticket.getRouteDetailsRequestId().compareTo(response.getRouteDetailRequestId()) == 0){
            double price = calculatePrice(response.getDistance(), ticket.getAmountOfSeats());
            this.bookTicketSaga.onRouteDetailsFetched(ticket, price, response.getStartStationName(), response.getEndStationName());
            this.ticketRepository.save(ticket);
        }
    }

    public synchronized void groupSeatsReserved(ReserveGroupSeatsResponse response){
        final Ticket ticket = ticketRepository.findById(response.getTicketId()).orElse(null);
        if(ticket != null && ticket.getReserveGroupSeatsRequestId().compareTo(response.getReserveGroupSeatsRequestId()) == 0){
            this.bookTicketSaga.onReservedGroupSeats(ticket);
            this.ticketRepository.save(ticket);
        }
    }

    public synchronized void failedToReserveGroupSeats(ReserveGroupSeatsResponse response){
        final Ticket ticket = ticketRepository.findById(response.getTicketId()).orElse(null);
        if(ticket != null && ticket.getReserveGroupSeatsRequestId().compareTo(response.getReserveGroupSeatsRequestId()) == 0){
            this.bookTicketSaga.onReserveGroupSeatsFailed(ticket);
            this.ticketRepository.save(ticket);
        }
    }

    public synchronized void failedToFetchDetails(RouteDetailResponse response){
        final Ticket ticket = ticketRepository.findById(response.getTicketId()).orElse(null);
        if(ticket != null && ticket.getRouteDetailsRequestId().compareTo(response.getRouteDetailRequestId()) == 0){
            this.bookTicketSaga.onRouteDetailsFetchingFailed(ticket);
            this.ticketRepository.save(ticket);
        }
    }


    private double calculatePrice(double distance, int amountOfSeats){
        double ticketPrice = Math.round(distance * PRICE_PER_KM * 10.0)/10.0;
        double totalPrice = ticketPrice >= MAX_PRICE ? MAX_PRICE : ticketPrice;
        return Math.round(totalPrice * amountOfSeats * 10.0)/10.0;
    }
}
