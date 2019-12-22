package com.railway.staff_service.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.staff_service.adapters.messaging.StaffResponse;
import com.railway.staff_service.persistence.StaffMembersRepository;

@Service
public class StaffService {
	private final StaffMembersRepository staffMembersRepository;
	
	@Autowired
	public StaffService(StaffMembersRepository staffMembersRepository) {
		this.staffMembersRepository=staffMembersRepository;
	}

	public StaffResponse reserveStaff(UUID requestId, int amount, StaffMemberType staffMemberType, LocalDateTime startDate,
			LocalDateTime endDate) {
		Collection<StaffMember> staffMembers = staffMembersRepository.findByStaffMemberType(staffMemberType);
		StaffResponse response = new StaffResponse(requestId);
		
		if (staffMembers.size() < amount) {
			response.setResponseMessage("Could not reserve enough staff members of requested type");
			return response;
		}
		
		Iterator<StaffMember> staffIterator = staffMembers.iterator();
		while(staffIterator.hasNext() && response.getStaffIds().size() != amount) {
			StaffMember staffMember = staffIterator.next();
			
			if(isStaffMemberAvailable(staffMember, startDate, endDate)) {
				response.addStaffId(staffMember.getId());
			}
		}
		
		if(response.getStaffIds().size() == amount) {
			reserveStaffMembers(requestId, response.getStaffIds(), startDate, endDate);
			response.setResponseMessage("Successfully reserved all requested staff members");
		} else {
			response.setStaffIds(new ArrayList<>());
			response.setResponseMessage("Could not reserve enough staff members for requested date");
		}
		
		return response;
	}

	private boolean isStaffMemberAvailable(StaffMember staffMember, LocalDateTime startDate, LocalDateTime endDate) {
		Collection<ScheduleItem> scheduleItems = staffMember.getScheduleItems();
		Iterator<ScheduleItem> scheduleIterator = scheduleItems.iterator();
		
		boolean isAvailable = true;
		while(scheduleIterator.hasNext()) {
			ScheduleItem scheduleItem = scheduleIterator.next();
			if(!(startDate.isBefore(scheduleItem.getStartDate()) && endDate.isBefore(scheduleItem.getStartDate()) || 
					startDate.isAfter(scheduleItem.getEndDate()) && endDate.isAfter(scheduleItem.getEndDate()))) {
				isAvailable = false;
			}
		}
		return isAvailable;
	}

	private void reserveStaffMembers(UUID requestId, Collection<String> staffIds, LocalDateTime startDate, LocalDateTime endDate) {
		ScheduleItem scheduleItem = new ScheduleItem(requestId, startDate, endDate);
		
		for(String staffId : staffIds) {
			StaffMember staffMember = staffMembersRepository.findById(staffId).orElse(null);
			staffMember.addScheduleItem(scheduleItem);
			staffMembersRepository.save(staffMember);
		}
	}

	public void discardStaffReservations(UUID requestId) {
		Iterable<StaffMember> staff = staffMembersRepository.findAll();
		
		for(StaffMember staffMember : staff) {
			Iterator<ScheduleItem> schedule = staffMember.getScheduleItems().iterator();
			while(schedule.hasNext()) {
				ScheduleItem scheduleItem = schedule.next();
				if(scheduleItem.getRequestId().compareTo(requestId) == 0) {
					schedule.remove();
					staffMembersRepository.save(staffMember);
				}
			}
		}
	}
}
