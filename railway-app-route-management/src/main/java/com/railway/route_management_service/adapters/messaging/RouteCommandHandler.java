package com.railway.route_management_service.adapters.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.railway.route_management_service.domain.Route;
import com.railway.route_management_service.domain.RouteService;

@Service
public class RouteCommandHandler {
	private final RouteService routeService;

	@Autowired
	public RouteCommandHandler(RouteService routeService) {
		this.routeService = routeService;
	}
	
	@StreamListener(Channels.GET_ROUTE)
	@SendTo(Channels.ROUTE_FETCHED)
	public Route getRoute(Route request) {
		return routeService.getRoute(request.getId());
	}
}
