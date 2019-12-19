package com.railway.delay_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String NOTIFY_DELAY = "notify_delay";
	static final String NOTIFY_DELAY_T = "notify_delay_t";
	
	@Output(NOTIFY_DELAY)
	MessageChannel delay();

	@Output(NOTIFY_DELAY_T)
	MessageChannel delay_t();
}
