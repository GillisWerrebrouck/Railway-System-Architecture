package com.railway.api_gateway.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.railway.api_gateway.adapters.messaging.EmergencyRequest;
import com.railway.api_gateway.adapters.messaging.InfrastructureDamageRequest;

@Service
public class ApiGatewayService {
	private static Logger logger = LoggerFactory.getLogger(ApiGatewayService.class);
	
	public ApiGatewayService() {}

	public void sendEmergencyRequest(EmergencyRequest request) {
		logger.info("EMERGENCY SERVICES CALL");
		logger.info("TimetableId: " + request.getTimetableId());
		logger.info("Details: " + request.getMessage());
	}
	
	public void sendInfrastructureDamageRequest(InfrastructureDamageRequest request) {
		logger.info("Damage on location: "+ request.getLocation() + " ");
		logger.info("Specifics: " + request.getMessage());
	}
}
