package com.railway.staff_service.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class StaffMember {
	@Id
	private String id;
	private String firstName;
	private String lastName;
	private Date birthdate;
	private StaffMemberType staffMemberType;
	
	public StaffMember() {}
	
	@PersistenceConstructor
	public StaffMember(String firstName, String lastName, Date birthdate, StaffMemberType staffMemberType) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.staffMemberType = staffMemberType;
	}
	
	public String getId() {
		return id;
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
	
	public Date getBirthdate() {
		return birthdate;
	}
	
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public StaffMemberType getStaffMemberType() {
		return staffMemberType;
	}
	
	public void setStaffMemberType(StaffMemberType staffMemberType) {
		this.staffMemberType = staffMemberType;
	}
	
	@Override
	public String toString() {
		return this.firstName + " " + this.lastName + "(" + this.staffMemberType.toString() + ")";
	}
}