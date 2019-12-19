package com.railway.route_management_service.adapters.messaging;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MessageGateway {
	@Gateway(requestChannel = Channels.GET_ROUTE_USAGE)
	public void getRouteUsage(RouteUsageRequest routeUsageRequest);

}
