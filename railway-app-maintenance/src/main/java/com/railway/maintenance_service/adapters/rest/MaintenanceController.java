package com.railway.maintenance_service.adapters.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railway.maintenance_service.domain.ScheduleItem;
import com.railway.maintenance_service.persistence.MaintenanceRepository;

@RestController
@RequestMapping(path="schedule")
public class MaintenanceController {
	private MaintenanceRepository maintenanceRepository;

	@Autowired
	public MaintenanceController(MaintenanceRepository maintenanceRepository) {
		this.maintenanceRepository = maintenanceRepository;
	}
	
	@GetMapping()
    public Iterable<ScheduleItem> getAllMaintenanceItems(){
		return maintenanceRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ScheduleItem getMaintenanceById(@PathVariable("id") Long id) {
		return maintenanceRepository.findById(id).orElse(null);
	}
}
