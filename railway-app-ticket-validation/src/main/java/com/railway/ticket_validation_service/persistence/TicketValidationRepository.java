package com.railway.ticket_validation_service.persistence;

import com.railway.ticket_validation_service.domain.TicketValidation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TicketValidationRepository extends CrudRepository<TicketValidation, UUID> {

}
