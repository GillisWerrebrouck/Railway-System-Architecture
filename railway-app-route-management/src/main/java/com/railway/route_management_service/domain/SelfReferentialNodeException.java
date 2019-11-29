package com.railway.route_management_service.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SelfReferentialNodeException extends RuntimeException {
	public SelfReferentialNodeException(String message) {
		super(message);
	}
}
