package com.railway.maintenance_service.adapters.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.railway.maintenance_service.adapters.messaging.InfrastructureDamageRequest;
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
	
	@PostMapping("/infrastructure_damage")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void notifyInfrastructureDamage(@RequestBody InfrastructureDamageRequest request) {
		maintenanceService.requestInfrastructureDamage(request);
	}
}