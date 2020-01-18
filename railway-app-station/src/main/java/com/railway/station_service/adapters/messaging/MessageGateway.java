package com.railway.station_service.adapters.messaging;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import com.railway.station_service.domain.Station;

@MessagingGateway
public interface MessageGateway {
	@Gateway(requestChannel = Channels.STATION_CREATED)
	public void stationCreated(Station station);

	@Gateway(requestChannel = Channels.STATION_DELETED)
	public void stationDeleted(Station station);
	
	@Gateway(requestChannel = Channels.GET_ROUTE_D)
	public void getRoute(RouteRequest routeRequest);
	
	@Gateway(requestChannel = Channels.NOTIFY_EXTRA_DELAY)
	public void notifyExtraDelay(UpdateDelayRequest updateDelayRequest);
}
