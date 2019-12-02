package com.railway.maintenance_service.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class Staff implements Serializable {
	private Long staffId;
	private String firstName;
	private String lastName;
	
	@SuppressWarnings("unused")
	private Staff() {}
	
	public Staff(Long staffId, String firstName, String lastName) {
		this.staffId = staffId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Long getStaffId() {
		return this.staffId;
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
		return "Staff [id=" + staffId + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}
