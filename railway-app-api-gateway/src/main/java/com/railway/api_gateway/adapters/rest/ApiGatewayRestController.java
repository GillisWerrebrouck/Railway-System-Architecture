package com.railway.api_gateway.adapters.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.railway.api_gateway.adapters.messaging.EmergencyRequest;
import com.railway.api_gateway.domain.ApiGatewayService;

@RestController
@RequestMapping("/api")
public class ApiGatewayRestController {

	private ApiGatewayService apiGatewayService;
	
	@Autowired
	public ApiGatewayRestController(ApiGatewayService apiGatewayService) {
		this.apiGatewayService = apiGatewayService;
	}
	
	@PostMapping("/notify_emergency")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void notifyEmergencyServices(@RequestBody EmergencyRequest emergencyRequest) {
		apiGatewayService.sendEmergencyRequest(emergencyRequest); 
	}
}
