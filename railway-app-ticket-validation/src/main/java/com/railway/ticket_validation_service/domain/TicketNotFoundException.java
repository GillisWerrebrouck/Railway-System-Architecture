package com.railway.ticket_validation_service.domain;

public class TicketNotFoundException extends Exception {
	public TicketNotFoundException(String message) {
		super(message);
	}
}
