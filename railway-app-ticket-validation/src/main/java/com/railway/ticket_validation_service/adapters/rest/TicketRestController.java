package com.railway.ticket_validation_service.adapters.rest;

import com.railway.ticket_validation_service.domain.Ticket;
import com.railway.ticket_validation_service.domain.TicketNotFoundException;
import com.railway.ticket_validation_service.persistence.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path="ticket")
public class TicketRestController {
    private TicketRepository ticketRepository;

    @Autowired
    public TicketRestController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping()
    public Iterable<Ticket> getAllTicketValidations(){
        return ticketRepository.findAll();
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable("id") UUID id) {
        return ticketRepository.findById(id).orElse(null);
    }

    @GetMapping("/validate/{id}")
    public Ticket validateTicket(@PathVariable("id") UUID id) throws TicketNotFoundException{
        Ticket ticket = ticketRepository.findById(id).orElse(null);

        if (ticket != null) {	
        	ticket.validate();	
            ticketRepository.save(ticket);	
        } else {
        	String errorMessage = "No ticket found with id '" + id.toString() + "'";
        	throw new TicketNotFoundException(errorMessage);
        }

        return ticket;
    }
}
