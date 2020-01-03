package com.railway.ticket_validation_service.adapters.rest;

import com.railway.ticket_validation_service.adapters.messaging.TicketValidationEventHandler;
import com.railway.ticket_validation_service.domain.Ticket;
import com.railway.ticket_validation_service.domain.TicketNotFoundException;
import com.railway.ticket_validation_service.persistence.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/ticket-validation")
public class TicketRestController {
    private TicketRepository ticketRepository;
    private static Logger logger = LoggerFactory.getLogger(TicketValidationEventHandler.class);

    @Autowired
    public TicketRestController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping()
    public Iterable<Ticket> getAllTicketValidations(){
        return ticketRepository.findAll();
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable("id") Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    @GetMapping("/validate/{validationCode}")
    public Ticket validateTicket(@PathVariable("validationCode") String validationCode) throws TicketNotFoundException{
        Ticket ticket = ticketRepository.findTicketByValidationCode(UUID.fromString(validationCode)).orElse(null);
        if (ticket != null) {
        	ticket.validate();
            ticketRepository.save(ticket);
        } else {
        	String errorMessage = "No ticket found with validationcode '" + validationCode.toString() + "'";
        	throw new TicketNotFoundException(errorMessage);
        }
        return ticket;
    }
}
