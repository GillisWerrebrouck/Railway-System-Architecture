package com.railway.maintenance_service.adapters.messaging;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import com.railway.maintenance_service.domain.MaintenanceService;
import com.railway.maintenance_service.domain.MaintenanceType;
import com.railway.maintenance_service.domain.ScheduleItem;
import com.railway.maintenance_service.domain.Status;
import com.railway.maintenance_service.persistence.MaintenanceRepository;

@Service
public class MaintenanceCommandHandler {
	private static Logger logger = LoggerFactory.getLogger(MaintenanceCommandHandler.class);
	private final MaintenanceService maintenanceService;
	private final MaintenanceRepository maintenanceRepository;

	@Autowired
	public MaintenanceCommandHandler(MaintenanceService maintenanceService, MaintenanceRepository maintenanceRepository) {
		this.maintenanceService = maintenanceService;
		this.maintenanceRepository = maintenanceRepository;
	}
	
	@StreamListener(Channels.REQUEST_MAINTENANCE)
	public void requestMaintenance(MaintenanceRequest request) {
		logger.info("[Maintenance Command Handler] request maintenance command received");
		
		// a maintenance is schedule to take 1 day by default
		LocalDateTime startDate = request.getMaintenanceDate();
		LocalDateTime endDate = request.getMaintenanceDate().plusDays(1);
		ScheduleItem scheduleItem = new ScheduleItem(request.getTrainId(), startDate, endDate, Status.SCHEDULED, request.getMaintenanceMessage(), MaintenanceType.MAINTENANCE);
		
		// reserve staff
		StaffRequest staffRequest = new StaffRequest(4, StaffMemberType.MECHANIC, startDate, endDate);
		scheduleItem.setRequestId(staffRequest.getRequestId());
		maintenanceService.reserveStaff(staffRequest);
		
		maintenanceRepository.save(scheduleItem);
	}
	
	@StreamListener(Channels.NOTIFY_ACCIDENT)
	public void notifyAccident(AccidentRequest request) {
		logger.info("[Maintenance Command Handler] notifyAccident received");
		
		// a maintenance is schedule to take 1 day by default
		LocalDateTime startDate = request.getAccidentDate();
		LocalDateTime endDate = request.getAccidentDate().plusHours(2);
		ScheduleItem scheduleItem = new ScheduleItem(request.getTrainId(), startDate, endDate, Status.SCHEDULED, request.getAccidentMessage(),  MaintenanceType.ACCIDENT);
		
		maintenanceRepository.save(scheduleItem);
	}
}
