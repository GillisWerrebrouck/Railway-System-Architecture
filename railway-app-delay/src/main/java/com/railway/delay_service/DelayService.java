package com.railway.delay_service;

import java.util.Set;

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
	//private final Set<HospitalCheckInListener> listeners;
	
	@Autowired
	public DelayService(MessageGateway gateway) {
		this.gateway = gateway;
		//this.listeners = new HashSet<>(5);
	}
	
	public void sendDelay(DelayRequest delayRequest) {
		gateway.delayOccured(delayRequest);
	}
}
