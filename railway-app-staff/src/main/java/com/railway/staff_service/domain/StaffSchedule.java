package com.railway.staff_service.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.util.Pair;

@Document
public class StaffSchedule {
	
	@Id
	private String id;
	private StaffMemberType type;
	private List<StaffMember> reservedStaffMembers;
	private List<StaffMember> availableStaffMembers;
	private HashMap<String,List<Pair<LocalDate,LocalDate>>> workHourList;
	
	public List<StaffMember> reserveStaff(List<Pair<LocalDate,LocalDate>> hours) 
			throws NotEnoughStaffException {
		List<StaffMember> staffMembers = new ArrayList<>();
		int availableStaff = availableStaffMembers.size();
		int requestedStaff = hours.size();
		if(availableStaff < requestedStaff) {
			throw new NotEnoughStaffException(requestedStaff,availableStaff - requestedStaff);
		} else {
			for (Iterator<Pair<LocalDate, LocalDate>> iterator = hours.iterator(); iterator.hasNext();) {
				StaffMember staffMember = availableStaffMembers.remove(0);
				reservedStaffMembers.add(staffMember);
				staffMembers.add((StaffMember) staffMember);
			}
		}
		return staffMembers;
	}

	public List<StaffMember> getReservedStaffMembers() {
		return reservedStaffMembers;
	}

	public void setReservedStaffMembers(List<StaffMember> reservedStaffMembers) {
		this.reservedStaffMembers = reservedStaffMembers;
	}

	public List<StaffMember> getAvailableStaffMembers() {
		return availableStaffMembers;
	}

	public void setAvailableStaffMembers(List<StaffMember> availableStaffMembers) {
		this.availableStaffMembers = availableStaffMembers;
	}

	public HashMap<String, List<Pair<LocalDate, LocalDate>>> getWorkHourList() {
		return workHourList;
	}

	public void setWorkHourList(HashMap<String, List<Pair<LocalDate, LocalDate>>> workHourList) {
		this.workHourList = workHourList;
	}

	public String getId() {
		return id;
	}

	public StaffMemberType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "StaffSchedule [id=" + id + ", type=" + type + ", reservedStaffMembers=" + reservedStaffMembers
				+ ", availableStaffMembers=" + availableStaffMembers + ", workHourList=" + workHourList + "]";
	}
}
