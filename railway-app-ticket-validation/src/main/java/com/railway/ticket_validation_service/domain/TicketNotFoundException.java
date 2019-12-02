package com.railway.ticket_validation_service.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TicketNotFoundException extends Exception {
	public TicketNotFoundException(String message) {
		super(message);
	}
}
