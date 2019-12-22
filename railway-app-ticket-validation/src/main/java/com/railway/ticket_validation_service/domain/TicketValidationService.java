package com.railway.ticket_validation_service.domain;

import com.railway.ticket_validation_service.persistence.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketValidationService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketValidationService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void addTicket(Ticket ticket){
        ticketRepository.save(ticket);
    }
}
