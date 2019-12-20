package com.railway.api_gateway.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.railway.api_gateway.adapters.messaging.EmergencyRequest;


@Service
public class ApiGatewayService {

	private static Logger logger = LoggerFactory.getLogger(ApiGatewayService.class);
	
	public ApiGatewayService() {

	}

	public void sendEmergencyRequest(EmergencyRequest request) {
		logger.info("called emergency services ---------------------  HELP!");
		logger.info("details: " + request.getMessage());
	}
}
