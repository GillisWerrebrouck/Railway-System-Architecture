package com.railway.route_management_service.domain;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.route_management_service.persistence.ConnectionRepository;
import com.railway.route_management_service.persistence.RouteRepository;

@Service
public class RouteService {
	private final ConnectionRepository connectionRepository;
	
	@Autowired
	public RouteService(ConnectionRepository connectionRepository) {
		this.connectionRepository = connectionRepository;
	}
	
	public Collection<Connection> getRoute(Long routeId) {
		return connectionRepository.findRouteById(routeId);
	}
}
