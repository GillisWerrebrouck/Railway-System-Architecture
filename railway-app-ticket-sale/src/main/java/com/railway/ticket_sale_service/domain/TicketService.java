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

import java.util.ArrayList;
import java.util.List;
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

    public synchronized void bookTickets(TicketRequest ticketRequest){
        if(ticketRequest.getTicketType() == TicketType.GROUP){
                Ticket groupTicket = new Ticket(ticketRequest.getStartDateTime(), ticketRequest.getTimetableId(), ticketRequest.getAmountOfSeats(), ticketRequest.getTicketCreationId());
                if(groupTicket.getAmountOfSeats() < 10) {
                    this.bookTicketSaga.onGroupTicketAmountOfSeatsToLow(groupTicket);
                }else {
                    this.ticketRepository.save(groupTicket);
                    this.bookTicketSaga.startBookGroupTicketSaga(groupTicket, UUID.fromString(ticketRequest.getEndStationId()),
                            UUID.fromString(ticketRequest.getStartStationId()));
                    this.ticketRepository.save(groupTicket);
                }
        }else{
            List<Ticket> singleTickets = new ArrayList<>();
            logger.info("amountofseats: " + ticketRequest.getAmountOfSeats());
            logger.info("startdatetime: " + ticketRequest.getStartDateTime());
            for(int i = 0; i < ticketRequest.getAmountOfSeats(); i++){
                singleTickets.add(new Ticket(ticketRequest.getStartDateTime(), 1, ticketRequest.getTicketCreationId()));
            }
            this.ticketRepository.saveAll(singleTickets);
            this.bookTicketSaga.startBookTicketSaga(singleTickets, UUID.fromString(ticketRequest.getEndStationId()),
                    UUID.fromString(ticketRequest.getStartStationId()));
            this.ticketRepository.saveAll(singleTickets);
        }
    }

    public synchronized void routeDetailsFetched(RouteDetailResponse response){
        List<Ticket> tickets = ticketRepository.findByIdIn(response.getTicketsIdList());
        tickets.removeIf(ticket -> ticket.getRouteDetailsRequestId().compareTo(response.getRouteDetailRequestId()) != 0);
        tickets.forEach(t -> t.setPrice(calculatePrice(response.getDistance(), t.getAmountOfSeats())));
        this.bookTicketSaga.onRouteDetailsFetched(tickets, response.getStartStationName(), response.getEndStationName());
        this.ticketRepository.saveAll(tickets);
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
            this.ticketRepository.delete(ticket);
        }
    }

    public synchronized void failedToFetchDetails(RouteDetailResponse response){
        List<Ticket> tickets = ticketRepository.findByIdIn(response.getTicketsIdList());
        tickets.removeIf(ticket -> ticket.getRouteDetailsRequestId().compareTo(response.getRouteDetailRequestId()) != 0);
        this.bookTicketSaga.onRouteDetailsFetchingFailed(tickets);
        this.ticketRepository.deleteAll(tickets);
    }

    private double calculatePrice(double distance, int amountOfSeats){
        double ticketPrice = Math.round(distance * PRICE_PER_KM * 10.0)/10.0;
        double totalPrice = ticketPrice >= MAX_PRICE ? MAX_PRICE : ticketPrice;
        return Math.round(totalPrice * amountOfSeats * 10.0)/10.0;
    }

}

