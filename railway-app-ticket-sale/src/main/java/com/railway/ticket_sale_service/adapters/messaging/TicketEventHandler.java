package com.railway.ticket_sale_service.adapters.messaging;

import com.railway.ticket_sale_service.domain.TicketService;
import com.railway.ticket_sale_service.persistence.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class TicketEventHandler {

    private static Logger logger = LoggerFactory.getLogger(TicketEventHandler.class);

    private final TicketService ticketService;

    @Autowired
    public TicketEventHandler(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @StreamListener(Channels.ROUTE_DETAILS_FETCHED)
    public void distanceFetched(RouteDetailResponse response) {
        logger.info("test");
        if(response.getDistance() > 0) {
            logger.info("[TicketSale Event Handler] successfully fetched distance");
            ticketService.bookTicket(response);
        } else {
            logger.info("[Timetable Event Handler] failed to fetch route");
        }
    }
}
