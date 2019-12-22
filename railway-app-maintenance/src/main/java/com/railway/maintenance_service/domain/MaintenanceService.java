package com.railway.maintenance_service.domain;

import org.springframework.stereotype.Service;

import com.railway.maintenance_service.adapters.messaging.InfrastructureDamageRequest;
import com.railway.maintenance_service.adapters.messaging.MessageGateway;
import com.railway.maintenance_service.adapters.messaging.StaffRequest;

@Service
public class MaintenanceService {
	private MessageGateway gateway;
	
	public MaintenanceService(MessageGateway gateway) {
		this.gateway = gateway;
	}
	
	public void reserveStaff(StaffRequest request) {
		gateway.reserveStaff(request);
	}
	
	public void requestInfrastructureDamage(InfrastructureDamageRequest infrastructureDamageRequest) {
		gateway.requestInfrastructureDamage(infrastructureDamageRequest);
	}
}
