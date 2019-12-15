package com.railway.delay_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Channels {
	static final String DELAY = "delay_occured";
	
	@Output(DELAY)
	MessageChannel delay();
	
}
