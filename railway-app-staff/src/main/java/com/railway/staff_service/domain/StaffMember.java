package com.railway.staff_service.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.util.Pair;

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
	
	private List<Pair<LocalDate,LocalDate>> availability;
	
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
	
	public List<Pair<LocalDate,LocalDate>> getAvailability() {
		return availability;
	}

	public void setAvailability(List<Pair<LocalDate,LocalDate>> availability) {
		this.availability = availability;
	}
	
	public StaffMemberType getStaffMemberType() {
		return staffMemberType;
	}

	@Override
	public String toString() {
		return "StaffMember [id=" + id + ", staffMemberId=" + staffMemberId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", age=" + age + ", staffMemberType=" + staffMemberType + ", availability=" + availability
				+ "]";
	}
}
