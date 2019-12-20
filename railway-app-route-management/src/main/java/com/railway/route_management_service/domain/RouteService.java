package com.railway.route_management_service.domain;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.railway.route_management_service.adapters.messaging.MessageGateway;
import com.railway.route_management_service.adapters.messaging.RouteDetailRequest;
import com.railway.route_management_service.adapters.messaging.RouteUsageRequest;
import com.railway.route_management_service.adapters.rest.UpdateRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.route_management_service.persistence.ConnectionRepository;
import com.railway.route_management_service.persistence.RouteRepository;

@Service
public class RouteService {
	private final ConnectionRepository connectionRepository;
	private final RouteRepository routeRepository;
	private final MessageGateway gateway;
	
	@Autowired
	public RouteService(ConnectionRepository connectionRepository, RouteRepository routeRepository, MessageGateway gateway) {
		this.connectionRepository = connectionRepository;
		this.routeRepository = routeRepository;
		this.gateway = gateway;
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

	//a connection can always be set active (if it exists) because before it was nonactive so it certainly wasnt used in timetable
	//if we want to set a connection non active then a check has to be performed
	public void updateRouteConnections(UpdateRequest request) {
		//get all routeids, each linked with a connectionid used in that route
		Collection<RouteAssociation> routeAssociations = routeRepository.getRouteAssociations();
		for (RouteAssociation ra : routeAssociations) {
			//if connectionids to be set (non)active contains current ras connectionid then proceed
			if (request.getConnectionIds().contains(ra.getConnectionId())) {
				//get connection
				Connection c = connectionRepository.findById(ra.getConnectionId()).get();
				//if connection exists
				if (c != null) {
					//if connection status equals action we want to do, then nothing has to be done
					if(c.isActive() != request.isActive()) {
						//if we want to set status to nonactive, we have to check if that is possible
						if(!request.isActive()) {
							RouteUsageRequest r = new RouteUsageRequest(ra.getRouteId(), ra.getConnectionId(), false);
							gateway.getRouteUsage(r);
						} else {
							c.setActive(request.isActive());
						}
					}
				}
			}
		}	
	}

	//eventhandler for getrouteUsage: response contains checked routeId, connectionId for which it was checked and a boolean isUsed
	public void onRouteUsageChecked(RouteUsageRequest response) {
		if(!response.isUsed()) {
			Connection c = connectionRepository.findById(response.getConnectionId()).get();
			c.setActive(false);
		} else {
			//was in gebruik, bericht teruggeven aan infrabel?
		}
	}
}
