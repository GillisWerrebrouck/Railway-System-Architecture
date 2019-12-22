package com.railway.timetable_service.domain;

public class NotEnoughGroupSeatsException extends Exception {
	public NotEnoughGroupSeatsException(String message) {
		super(message);
	}
}
