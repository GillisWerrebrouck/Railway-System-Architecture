package com.railway.route_management_service.adapters.messaging;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.railway.route_management_service.domain.Connection;
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
	public RouteResponse getRoute(RouteRequest request) {
		Collection<Connection> route = routeService.getRoute(request.getRouteId());
		RouteResponse response = new RouteResponse(request.getRouteId(), request.getTimetableId(), request.getRequestId(), route);
		return response;		
	}
}
