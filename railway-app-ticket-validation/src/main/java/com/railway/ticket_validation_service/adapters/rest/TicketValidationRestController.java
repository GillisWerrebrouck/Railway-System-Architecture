package com.railway.ticket_validation_service.adapters.rest;

import com.railway.ticket_validation_service.domain.TicketValidation;
import com.railway.ticket_validation_service.persistence.TicketValidationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path="ticket-validation")
public class TicketValidationRestController {

    private TicketValidationRepository ticketValidationRepository;

    @Autowired
    public TicketValidationRestController(TicketValidationRepository ticketValidationRepository) {
        this.ticketValidationRepository = ticketValidationRepository;
    }

    @GetMapping()
    public Iterable<TicketValidation> getAllTicketValidations(){
        return ticketValidationRepository.findAll();
    }

    @GetMapping("/{id}")
    public TicketValidation getTicketById(@PathVariable("id") UUID id) {
        return ticketValidationRepository.findById(id).orElse(null);
    }

    @PostMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void validateTicket(@PathVariable("id") UUID id){
        TicketValidation ticketValidation = ticketValidationRepository.findById(id).orElse(null);
        ticketValidation.setUsed(true);
        ticketValidationRepository.save(ticketValidation);
    }

}
