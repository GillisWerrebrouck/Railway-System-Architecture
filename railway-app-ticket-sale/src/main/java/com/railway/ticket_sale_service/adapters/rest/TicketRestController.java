package com.railway.ticket_sale_service.adapters.rest;

import com.railway.ticket_sale_service.domain.Ticket;
import com.railway.ticket_sale_service.persistence.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="ticket")
public class TicketRestController {
    private TicketRepository ticketRepository;

    @Autowired
    public TicketRestController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping
    public Iterable<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable("id") Long id){
        return ticketRepository.findById(id).orElse(null);
    }
}
