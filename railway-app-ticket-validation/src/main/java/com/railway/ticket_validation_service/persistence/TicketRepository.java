package com.railway.ticket_validation_service.persistence;

import com.railway.ticket_validation_service.domain.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TicketRepository extends CrudRepository<Ticket, UUID> {
	
}
