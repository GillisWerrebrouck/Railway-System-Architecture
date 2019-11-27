package com.railway.staff_service.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.railway.staff_service.domain.StaffMemberType;
import com.railway.staff_service.domain.ScheduleRecord;

public interface ScheduleRecordRepository extends MongoRepository<ScheduleRecord, String> {
	
	List<ScheduleRecord> findByStaffMemberType(StaffMemberType staffMemberType);
	
	List<ScheduleRecord> findByStartDate(LocalDate startDate);

	List<ScheduleRecord> findByEndDate(LocalDate endDate);
	
	List<ScheduleRecord> findByBooked(boolean booked);
	
	List<ScheduleRecord> findByScheduleRecordId(String scheduleRecordId);
}