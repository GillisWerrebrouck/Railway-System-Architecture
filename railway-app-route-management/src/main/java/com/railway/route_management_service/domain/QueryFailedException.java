package com.railway.route_management_service.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class QueryFailedException extends RuntimeException {
	public QueryFailedException(String message) {
		super(message);
	}
}
