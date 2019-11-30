package com.railway.maintenance_service.domain;

public class Staff {
	
	private Long Id;
	private String firstName;
	private String lastName;
	
	public Staff(Long Id, String firstName, String lastName) {
		this.Id = Id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Long getId() {
		return this.Id;
	}
	
	public void setId(Long Id) {
		this.Id = Id;
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
		return "Staff [staffId=" + Id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
}
