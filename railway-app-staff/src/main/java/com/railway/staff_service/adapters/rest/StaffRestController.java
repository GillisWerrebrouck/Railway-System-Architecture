package com.railway.staff_service.adapters.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.railway.staff_service.domain.ScheduleRecord;
import com.railway.staff_service.domain.StaffMember;
import com.railway.staff_service.domain.StaffService;
import com.railway.staff_service.persistence.ScheduleRecordRepository;
import com.railway.staff_service.persistence.StaffMembersRepository;

@RestController
@RequestMapping("/staff")
public class StaffRestController {
	private ScheduleRecordRepository scheduleStaffRepository;
	private final StaffMembersRepository staffMembersRepository;
	
	private final Map<String, DeferredResult<ScheduleRecord>> deferredResultsScheduleRecords;
	private final Map<String, DeferredResult<StaffMember>> deferredResultsStaffMember;
	private StaffService staffService;
	
	@Autowired
	private StaffRestController(ScheduleRecordRepository scheduleStaffRepository, StaffMembersRepository staffMembersRepository, StaffService staffService) {
		this.deferredResultsStaffMember = new HashMap<>(100);
		this.scheduleStaffRepository = scheduleStaffRepository;
		this.staffMembersRepository = staffMembersRepository;
		this.staffService = staffService;
		this.deferredResultsScheduleRecords = new HashMap<>(900);
	}
	
	@GetMapping("/staff_schedule_records")
	public Iterable<ScheduleRecord> getAllScheduleRecords(){
		return this.scheduleStaffRepository.findAll();
	}
	
	@GetMapping("/staff_members")
	public Iterable<StaffMember> getAllStaffMembers(){
		return this.staffMembersRepository.findAll();
	}
	
	private void performResponseRecord(ScheduleRecord response) {
		DeferredResult<ScheduleRecord> deferredResult = this.deferredResultsScheduleRecords.remove(response.getScheduleRecordId());
		if(deferredResult != null && !deferredResult.isSetOrExpired()) {
			System.out.println("Setting result");
			deferredResult.setResult(response);
		} else {
			System.out.println("deferredResult: " + deferredResult);
		}
	}
	
	public void onScheduleRecordReservationResult(ScheduleRecord scheduleRecord) {
		this.performResponseRecord(scheduleRecord);
	}
	
	private void performResponseStaff(StaffMember response) {
		DeferredResult<StaffMember> deferredResult = this.deferredResultsStaffMember.remove(response.getStaffMemberId());
		if(deferredResult != null && !deferredResult.isSetOrExpired()) {
			System.out.println("Setting result");
			deferredResult.setResult(response);
		} else {
			System.out.println("deferredResult: " + deferredResult);
		}
	}
	
	public void onStaffMemberReservationResult(StaffMember staffMember) {
		this.performResponseStaff(staffMember);
	}
}
