package com.railway.ticket_validation_service.adapters.messaging;

import com.railway.ticket_validation_service.domain.Ticket;
import com.railway.ticket_validation_service.domain.TicketValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class TicketValidationEventHandler {
    private static Logger logger = LoggerFactory.getLogger(TicketValidationEventHandler.class);

    private final TicketValidationService ticketValidationService;

    @Autowired
    public TicketValidationEventHandler(TicketValidationService ticketValidationService) {
        this.ticketValidationService = ticketValidationService;
    }

    @StreamListener(Channels.TICKET_CREATED)
    public void addTicket(Ticket ticket){
    	logger.info("[Ticket Validation Event Handler] ticket created event received");
        ticketValidationService.addTicket(ticket);
    }
}
