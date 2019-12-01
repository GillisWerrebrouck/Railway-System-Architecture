package com.railway.timetable_service.adapters.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railway.timetable_service.domain.TimetableItem;
import com.railway.timetable_service.persistence.TimetableItemRepository;

@RestController
@RequestMapping("/timetable")
public class TimetableItemRestController {
	private TimetableItemRepository timetableItemRepository;
	
	@Autowired
	private TimetableItemRestController(TimetableItemRepository timetableItemRepository) {
		this.timetableItemRepository = timetableItemRepository;
	}
	
	@GetMapping
	private Iterable<TimetableItem> getAllTimetableItems() {
		return timetableItemRepository.findAll();
	}
	
	@GetMapping("/route/{routeId}")
	private Iterable<TimetableItem> getTimetableItemByRouteId(@PathVariable Long routeId) {
		return timetableItemRepository.findByRouteId(routeId);
	}
	
	@GetMapping("/{id}")
	private Optional<TimetableItem> getTimetableItemById(@PathVariable Long id) {
		return timetableItemRepository.findById(id);
	}
}
