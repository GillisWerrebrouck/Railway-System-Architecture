package com.railway.train_service.adapters.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
	static final String RESERVE_TRAIN = "reserve_train";
	static final String TRAIN_RESERVED = "train_reserved";
	
	@Input(RESERVE_TRAIN)
	SubscribableChannel reserveTrain();
	
	@Output(TRAIN_RESERVED)
	MessageChannel trainReserved();
}
