package com.railway.maintenance_service.domain;

public class Staff {
	
	private String staffId;
	private String firstName;
	private String lastName;
	
	public Staff(String staffId, String firstName, String lastName) {
		this.staffId = staffId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getStaffId() {
		return staffId;
	}
	
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Staff [staffId=" + staffId + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
}
