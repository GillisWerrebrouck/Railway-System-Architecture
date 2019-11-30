package com.railway.staff_service.adapters.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railway.staff_service.domain.ScheduleItem;
import com.railway.staff_service.persistence.ScheduleItemRepository;

@RestController
@RequestMapping("/schedule")
public class ScheduleRestController {
	private final ScheduleItemRepository scheduleItemRepository;
	
	@Autowired
	private ScheduleRestController(ScheduleItemRepository scheduleItemRepository) {
		this.scheduleItemRepository = scheduleItemRepository;
	}
	
	@GetMapping
	public Iterable<ScheduleItem> getAllScheduleItems(){
		return this.scheduleItemRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<ScheduleItem> getScheduleItemById(@PathVariable String id){
		return this.scheduleItemRepository.findById(id);
	}
}
