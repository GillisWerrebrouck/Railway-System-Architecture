package com.railway.maintenance_service.adapters.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.railway.maintenance_service.adapters.messaging.ChangeStatusRequest;
import com.railway.maintenance_service.domain.MaintenanceService;
import com.railway.maintenance_service.domain.ScheduleItem;
import com.railway.maintenance_service.persistence.MaintenanceRepository;

@RestController
@RequestMapping("/maintenance/schedule")
public class MaintenanceController {
	private MaintenanceRepository maintenanceRepository;
	private MaintenanceService maintenanceService;

	@Autowired
	public MaintenanceController(MaintenanceRepository maintenanceRepository, MaintenanceService maintenanceService) {
		this.maintenanceRepository = maintenanceRepository;
		this.maintenanceService = maintenanceService;
	}
	
	@GetMapping
    public Iterable<ScheduleItem> getAllMaintenanceItems(){
		return maintenanceRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ScheduleItem getMaintenanceById(@PathVariable("id") Long id) {
		return maintenanceRepository.findById(id).orElse(null);
	}
	
	@PostMapping
	public void changeTrainStatus(@RequestBody ChangeStatusRequest request) {
		maintenanceService.changeTrainStatus(request.getTrainId(), request.getStatus());
	}
}
