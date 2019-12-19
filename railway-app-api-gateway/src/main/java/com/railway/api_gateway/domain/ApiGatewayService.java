package com.railway.api_gateway.domain;

import org.springframework.stereotype.Service;

import com.railway.api_gateway.adapters.messaging.EmergencyRequest;
import com.railway.api_gateway.adapters.messaging.MessageGateway;

@Service
public class ApiGatewayService {
	private MessageGateway gateway;

	public ApiGatewayService(MessageGateway gateway) {
		this.gateway = gateway;
	}
	
	public void sendEmergencyRequest(EmergencyRequest emergencyRequest) {
		this.gateway.notifyEmergencyServices(emergencyRequest); 
	}
}
