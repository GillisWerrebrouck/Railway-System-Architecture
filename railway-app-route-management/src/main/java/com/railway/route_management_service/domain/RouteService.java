package com.railway.route_management_service.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.route_management_service.persistence.RouteRepository;

@Service
public class RouteService {
	private final RouteRepository routeRepository;
	
	@Autowired
	public RouteService(RouteRepository routeRepository) {
		this.routeRepository = routeRepository;
	}
	
	public Route getRoute(Long routeId) {
		return routeRepository.findById(routeId).orElse(null);
	}
}
