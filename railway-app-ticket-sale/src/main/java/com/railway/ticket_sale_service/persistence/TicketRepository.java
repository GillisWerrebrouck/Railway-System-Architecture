package com.railway.ticket_sale_service.persistence;

import com.railway.ticket_sale_service.domain.Ticket;
import com.railway.ticket_sale_service.domain.TicketType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
    Iterable<Ticket> getTicketsByValidOnAfter(LocalDateTime date);

    Iterable<Ticket> getTicketsByType(TicketType type);
}
