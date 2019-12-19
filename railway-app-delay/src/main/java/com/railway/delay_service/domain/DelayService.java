package com.railway.delay_service.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
		gateway.delayOccured_t(delayRequest);
		gateway.delayOccured(delayRequest);
		
	}
}   
