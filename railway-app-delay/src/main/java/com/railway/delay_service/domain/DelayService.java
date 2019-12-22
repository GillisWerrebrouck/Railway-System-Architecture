package com.railway.delay_service.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.endpoint.BindingsEndpoint.State;
import org.springframework.stereotype.Service;

import com.railway.delay_service.adapters.messaging.DelayRequest;
import com.railway.delay_service.adapters.messaging.MessageGateway;

@Service
public class DelayService {
	private static Logger logger = LoggerFactory.getLogger(DelayService.class);
	private final MessageGateway gateway;
	
	@Autowired
	public DelayService(MessageGateway gateway) {
		this.gateway = gateway;
	}
	
	public void sendDelay(DelayRequest delayRequest) {
		logger.info("[Delay Service - sendDelay]" + delayRequest.getTimetableId() + ", " + delayRequest.getRouteId());
		//processing delay started
		delayRequest.setState(DelayState.STARTED);
		//send delay to timetable
		gateway.delayOccured_t(delayRequest);
		//send delay to station
		gateway.delayOccured(delayRequest);		
	}
}   
