package com.railway.timetable_service.domain;

@SuppressWarnings("serial")
public class NotEnoughGroupSeatsException extends Exception {
	public NotEnoughGroupSeatsException(String message) {
		super(message);
	}
}
