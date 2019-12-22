package com.railway.route_management_service.adapters.messaging;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.railway.route_management_service.domain.Connection;
import com.railway.route_management_service.domain.Route;
import com.railway.route_management_service.domain.RouteService;

@Service
public class RouteCommandHandler {
	private static Logger logger = LoggerFactory.getLogger(RouteCommandHandler.class);
	private final RouteService routeService;

	@Autowired
	public RouteCommandHandler(RouteService routeService) {
		this.routeService = routeService;
	}
	
	@StreamListener(Channels.GET_ROUTE)
	@SendTo(Channels.ROUTE_FETCHED)
	public RouteResponse getRoute(RouteRequest request) {
		logger.info("[Route Command Handler] get route command received");
		Collection<Connection> routeConnections = routeService.getRouteConnections(request.getRouteId());
		Route route = routeService.getRoute(request.getRouteId());
		RouteResponse response = new RouteResponse(request.getRouteId(), request.getTimetableId(), request.getRequestId(), routeConnections, route);
		logger.info("[Route Command Handler] route fetched");
		return response;
	}
}
