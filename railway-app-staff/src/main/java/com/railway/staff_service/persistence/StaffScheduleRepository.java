package com.railway.staff_service.persistence;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.railway.staff_service.domain.StaffMemberType;
import com.railway.staff_service.domain.StaffSchedule;

public interface StaffScheduleRepository extends MongoRepository<StaffSchedule, String> {
	
	public List<StaffSchedule> findByType(StaffMemberType type);
}
