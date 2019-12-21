package com.railway.route_management_service.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.railway.route_management_service.adapters.messaging.MessageGateway;
import com.railway.route_management_service.adapters.messaging.RouteUsageRequest;
import com.railway.route_management_service.adapters.messaging.RouteUsageResponse;
import com.railway.route_management_service.adapters.rest.UpdateConnectionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.route_management_service.persistence.ConnectionRepository;
import com.railway.route_management_service.persistence.RouteRepository;

@Service
public class RouteService {
	private final ConnectionRepository connectionRepository;
	private final RouteRepository routeRepository;
	private final MessageGateway gateway;
	private final Set<UpdateConnectionListener> listeners;
	
	@Autowired
	public RouteService(ConnectionRepository connectionRepository, RouteRepository routeRepository, MessageGateway gateway) {
		this.connectionRepository = connectionRepository;
		this.routeRepository = routeRepository;
		this.gateway = gateway;
		this.listeners = new HashSet<>(5);
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

	public void checkRouteUsage(Long connectionId) {
		Collection<Route> routes = routeRepository.getAllRoutesByConnectionId(connectionId);
		
		List<Long> routeIds = new ArrayList<>();
		routes.forEach(r -> routeIds.add(r.getId()));
		
		RouteUsageRequest request = new RouteUsageRequest(routeIds, connectionId);
		gateway.getRouteUsage(request);
	}

	public void updateConnection(RouteUsageResponse response) {
		// update connection if not used
		if (!response.isUsed()) {
			Connection connection = connectionRepository.findById(response.getConnectionId()).orElse(null);
			if(connection == null) return;
			connection.setActive(response.isActive());
			connectionRepository.save(connection);
		}
		
		this.listeners.forEach(l -> l.onResult(response));
	}

	public void registerUpdateConnectionListener(UpdateConnectionListener listener) {
		this.listeners.add(listener);
	}
}
