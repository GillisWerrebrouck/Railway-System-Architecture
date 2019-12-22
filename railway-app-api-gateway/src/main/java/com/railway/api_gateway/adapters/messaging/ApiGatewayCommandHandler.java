package com.railway.api_gateway.adapters.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import com.railway.api_gateway.domain.ApiGatewayService;


@Service
public class ApiGatewayCommandHandler {
	
    
    private final ApiGatewayService APIService;
    
    @Autowired
    public ApiGatewayCommandHandler(ApiGatewayService APIService) {
    	this.APIService = APIService;
    }

	@StreamListener(Channels.NOTIFY_EMERGENCY_SERVICES)
	public void notifyErmergencyServices(EmergencyRequest request) {
		APIService.sendEmergencyRequest(request);
	}
	
	@StreamListener(Channels.NOTIFY_INFRASTRUCTURE_DAMAGE)
	public void notifyInfrastructureDamage(InfrastructureDamageRequest request) {
		APIService.sendInfrastructureDamageRequest(request);
	}
}
