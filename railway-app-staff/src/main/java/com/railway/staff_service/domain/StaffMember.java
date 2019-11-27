package com.railway.staff_service.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class StaffMember {
	
	@Id
	private String id;
	
	private String staffMemberId;
	
	@Field("fName")
	private String firstName;
	
	private String lastName;
	private Integer age;
	
	private StaffMemberType staffMemberType;
	
	private List<HourSet> workHours;
	
	public StaffMember() {
		this.firstName = null;
		this.lastName = null;
		this.age = null;
		this.staffMemberType = null;
	}
	
	@PersistenceConstructor
	public StaffMember(String firstName, String lastName, int age,StaffMemberType staffMemberType) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.staffMemberType = staffMemberType;
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

	public String getStaffMemberId() {
		return staffMemberId;
	}

	public void setStaffMemberId(String staffMemberId) {
		this.staffMemberId = staffMemberId;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getId() {
		return id;
	}
	
	public StaffMemberType getStaffMemberType() {
		return staffMemberType;
	}

	public List<HourSet> getWorkHours() {
		return workHours;
	}

	public void setWorkHours(List<HourSet> workHours) {
		this.workHours = workHours;
	}
}