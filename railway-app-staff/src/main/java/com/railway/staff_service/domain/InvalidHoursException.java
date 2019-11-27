package com.railway.staff_service.domain;

public class InvalidHoursException extends Exception {

	private static final long serialVersionUID = 4398824901146866411L;

	public InvalidHoursException(HourSet input, HourSet hourset) {
		System.err.println("The hours you presented " +input.toString() + " are in conflict with other hours from this staff member: "+hourset);
	}

}
