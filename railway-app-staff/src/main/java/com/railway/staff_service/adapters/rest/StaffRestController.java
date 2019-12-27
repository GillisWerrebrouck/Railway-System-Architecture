package com.railway.staff_service.adapters.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.railway.staff_service.domain.StaffMember;
import com.railway.staff_service.persistence.StaffMembersRepository;

@RestController
@RequestMapping("/staff")
public class StaffRestController {
	private final StaffMembersRepository staffMembersRepository;
	
	@Autowired
	private StaffRestController(StaffMembersRepository staffMembersRepository) {
		this.staffMembersRepository = staffMembersRepository;
	}
	
	@GetMapping
	public Iterable<StaffMember> getAllStaffMembers(){
		return this.staffMembersRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<StaffMember> getStaffMemberById(@PathVariable String id){
		return this.staffMembersRepository.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void createStaff(@RequestBody StaffMember staff) throws Exception {
		try {
			this.staffMembersRepository.save(staff);
		} catch(Exception e) {
			throw new Exception("Could not create staff member");
		}
	}
}
