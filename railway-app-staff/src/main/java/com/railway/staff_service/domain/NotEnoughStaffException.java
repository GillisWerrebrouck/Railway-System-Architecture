package com.railway.staff_service.domain;


public class NotEnoughStaffException extends Exception {

	public NotEnoughStaffException(int requestedAmount, int overshoot) {
		System.err.println("You requested " + requestedAmount + " staff members, " + 
				overshoot + " staff members could not be found.\n Try " + (requestedAmount-overshoot) + ".");
	}

}
