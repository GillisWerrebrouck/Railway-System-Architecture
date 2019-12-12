package com.railway.staff_service.adapters.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.railway.staff_service.domain.StaffService;

@Service
public class StaffCommandHandler {
	private static Logger logger = LoggerFactory.getLogger(StaffCommandHandler.class);
	private StaffService staffService;

	@Autowired
	public StaffCommandHandler(StaffService staffService) {
		this.staffService = staffService;
	}
	
	@StreamListener(Channels.RESERVE_STAFF)
	@SendTo(Channels.STAFF_RESERVED)
	public StaffResponse requestMaintenance(StaffRequest request) {
		logger.info("[Staff Command Handler] reserve staff command received");
		
		StaffResponse response = staffService.reserveStaff(request.getRequestId(), request.getAmount(), request.getStaffMemberType(), request.getStartDate(), request.getEndDate());
		return response;
	}
}
