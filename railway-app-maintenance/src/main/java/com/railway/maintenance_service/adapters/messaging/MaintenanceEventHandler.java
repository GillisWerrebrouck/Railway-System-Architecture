package com.railway.maintenance_service.adapters.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import com.railway.maintenance_service.domain.ScheduleItem;
import com.railway.maintenance_service.persistence.MaintenanceRepository;

@Service
public class MaintenanceEventHandler {
	private static Logger logger = LoggerFactory.getLogger(MaintenanceEventHandler.class);
	private final MaintenanceRepository maintenanceRepository;

	@Autowired
	public MaintenanceEventHandler(MaintenanceRepository maintenanceRepository) {
		this.maintenanceRepository = maintenanceRepository;
	}
	
	@StreamListener(Channels.STAFF_RESERVED)
	public void requestMaintenance(StaffResponse response) {
		logger.info("[Maintenance Event Handler] staff reserved event received");
		
		ScheduleItem scheduleItem = maintenanceRepository.findByRequestId(response.getRequestId());
		scheduleItem.setStaffIds(response.getStaffIds());
		scheduleItem.setStaffReservationMessage(response.getResponseMessage());
		
		maintenanceRepository.save(scheduleItem);
	}
}
