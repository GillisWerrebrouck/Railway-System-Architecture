package com.railway.staff_service.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.staff_service.persistence.ScheduleItemRepository;
import com.railway.staff_service.persistence.StaffMembersRepository;

@Service
public class StaffService {
	private final ScheduleItemRepository scheduleRecordRepository;
	private final StaffMembersRepository staffMembersRepository;
	
	@Autowired
	public StaffService(ScheduleItemRepository scheduleRecordRepository, StaffMembersRepository staffMembersRepository) {
		this.scheduleRecordRepository=scheduleRecordRepository;
		this.staffMembersRepository=staffMembersRepository;
	}
}
