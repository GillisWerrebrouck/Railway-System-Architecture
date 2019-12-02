package com.railway.ticket_sale_service.persistence;

import com.railway.ticket_sale_service.domain.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t WHERE type = com.railway.ticket_sale_service.domain.TicketType.GROUP")
    Iterable<Ticket> getAllGroupTickets();

    @Query("SELECT t FROM Ticket t WHERE type = com.railway.ticket_sale_service.domain.TicketType.SINGLE")
    Iterable<Ticket> getAllSingleTickets();

    Iterable<Ticket> getTicketsByValidOnAfter(LocalDateTime date);
}
