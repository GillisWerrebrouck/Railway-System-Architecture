package com.railway.delay_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String NOTIFY_DELAY = "notify_delay";
	
	@Output(NOTIFY_DELAY)
	MessageChannel delay();

}
