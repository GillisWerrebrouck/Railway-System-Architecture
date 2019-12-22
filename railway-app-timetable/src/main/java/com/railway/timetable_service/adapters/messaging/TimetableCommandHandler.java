package com.railway.timetable_service.adapters.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;


import com.railway.timetable_service.domain.TimetableService;

@Service
public class TimetableCommandHandler {

	private static Logger logger = LoggerFactory.getLogger(TimetableCommandHandler.class);
	private final TimetableService timetableService;

	@Autowired
	public TimetableCommandHandler(TimetableService timetableService) {
		this.timetableService = timetableService;
	}
	
	@StreamListener(Channels.NOTIFY_DELAY)
	public void notifyDelay(DelayRequest request) {
		logger.info("[Timetable Command Handler] notify delay command received");
		timetableService.processDelay(request);
	}
}
