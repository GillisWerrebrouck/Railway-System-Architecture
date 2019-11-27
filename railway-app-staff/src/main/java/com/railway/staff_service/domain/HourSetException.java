package com.railway.staff_service.domain;

import java.time.LocalDate;

public class HourSetException extends Exception {

	private static final long serialVersionUID = 2211858019233399638L;

	public HourSetException(LocalDate startDate, LocalDate endDate) {
		System.err.println(startDate + " comes after " + endDate);
	}

}
