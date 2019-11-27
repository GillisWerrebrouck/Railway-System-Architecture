package com.railway.staff_service.persistence;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.railway.staff_service.domain.StaffMember;
import com.railway.staff_service.domain.StaffMemberType;

public interface StaffMembersRepository extends MongoRepository<StaffMember, String> {
	
	List<StaffMember> findByStaffMemberId(String staffMemberId);
	
	List<StaffMember> findStaffMemberByFirstName(String firstName);
	
	List<StaffMember> findStaffMemberByLastName(String lastName);
	
	List<StaffMember> findByStaffMemberType(StaffMemberType staffMemberType);
	
}