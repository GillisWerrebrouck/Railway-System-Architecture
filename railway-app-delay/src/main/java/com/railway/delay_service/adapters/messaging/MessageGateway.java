package com.railway.delay_service.adapters.messaging;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MessageGateway {

	@Gateway(requestChannel = Channels.NOTIFY_DELAY)
	public void delayOccured(DelayRequest delayRequest);

}
