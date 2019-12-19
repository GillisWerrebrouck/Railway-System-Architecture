package com.railway.route_management_service.domain;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.railway.route_management_service.adapters.messaging.RouteDetailRequest;
import com.railway.route_management_service.adapters.rest.UpdateRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.route_management_service.persistence.ConnectionRepository;
import com.railway.route_management_service.persistence.RouteRepository;

@Service
public class RouteService {
	private final ConnectionRepository connectionRepository;
	private final RouteRepository routeRepository;
	
	@Autowired
	public RouteService(ConnectionRepository connectionRepository, RouteRepository routeRepository) {
		this.connectionRepository = connectionRepository;
		this.routeRepository = routeRepository;
	}
	
	public Collection<Connection> getRouteConnections(Long routeId) {
		return connectionRepository.findRouteById(routeId);
	}
	
	public Route getRoute(Long routeId) {
		return routeRepository.findById(routeId).orElse(null);
	}

	public RouteDetails getRouteDetails(UUID stationX, UUID stationY){
		return routeRepository.getRouteDetails(stationX, stationY);
	}

	public void updateRouteConnections(UpdateRequest request) {
		Collection<RouteAssociation> routeAssociations = routeRepository.getRouteAssociations();
		for (RouteAssociation ra : routeAssociations) {
			if (routeAssociations.contains(ra.getConnectionId())) {
				Connection c = connectionRepository.findById(ra.getConnectionId()).get();
				if (c != null) {
					if(!c.isActive() == request.isActive()) {
						if(!request.isActive()) {
							checkRouteUsage(request);
						} else {
							c.setActive(request.isActive());
						}
					}
				}
			}
		}	
	}

	private void checkRouteUsage(UpdateRequest request) {
		// TODO Auto-generated method stub
		
	}
}
