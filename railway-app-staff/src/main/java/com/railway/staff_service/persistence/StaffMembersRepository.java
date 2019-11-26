package com.railway.staff_service.persistence;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.railway.staff_service.domain.StaffMember;
import com.railway.staff_service.domain.StaffMemberType;

public interface StaffMembersRepository extends MongoRepository<StaffMember, String> {
	
	public List<StaffMember> findByStaffMemberId(String StaffMemberId);
	
	public List<StaffMember> findStaffMemberByFirstName(String name);
	
	public List<StaffMember> findStaffMemberByLastName(String name);
	
	public List<StaffMember> findByStaffMemberType(StaffMemberType type);
}
