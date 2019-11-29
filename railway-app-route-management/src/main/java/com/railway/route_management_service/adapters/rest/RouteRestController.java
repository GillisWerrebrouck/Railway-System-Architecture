package com.railway.route_management_service.adapters.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.railway.route_management_service.domain.Connection;
import com.railway.route_management_service.persistence.ConnectionRepository;
import com.railway.route_management_service.persistence.StationRepository;

@RestController
@RequestMapping("/network")
public class RouteRestController {
	protected final StationRepository stationRepository;
	protected final ConnectionRepository connectionRepository;
	
	@Autowired
	public RouteRestController(StationRepository stationRepository, ConnectionRepository connectionRepository) {
		this.stationRepository = stationRepository;
		this.connectionRepository = connectionRepository;
	}

	// get the shortest (distance) route between two station nodes
	@GetMapping("/route")
	public Collection<Connection> getShortestPath(@RequestParam String startStation, @RequestParam String endStation){
		return this.connectionRepository.findShortestPath(startStation, endStation);
	}
}
