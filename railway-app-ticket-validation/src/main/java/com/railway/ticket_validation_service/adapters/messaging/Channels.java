package com.railway.ticket_validation_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

    static final String TICKET_CREATED = "ticket_created";

    @Input(TICKET_CREATED)
    SubscribableChannel ticketCreated();
}
