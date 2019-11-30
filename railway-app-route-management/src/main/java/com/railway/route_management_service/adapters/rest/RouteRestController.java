package com.railway.route_management_service.adapters.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.railway.route_management_service.domain.Connection;
import com.railway.route_management_service.domain.QueryFailedException;
import com.railway.route_management_service.domain.Route;
import com.railway.route_management_service.persistence.ConnectionRepository;
import com.railway.route_management_service.persistence.RouteRepository;
import com.railway.route_management_service.persistence.StationRepository;

@RestController
@RequestMapping("/network")
public class RouteRestController {
	protected final StationRepository stationRepository;
	protected final ConnectionRepository connectionRepository;
	protected final RouteRepository routeRepository;
	
	@Autowired
	public RouteRestController(StationRepository stationRepository, ConnectionRepository connectionRepository, RouteRepository routeRepository) {
		this.stationRepository = stationRepository;
		this.connectionRepository = connectionRepository;
		this.routeRepository = routeRepository;
	}
	
	@GetMapping("/route")
	public Iterable<Route> getAllRoutes() {
		return this.routeRepository.findAll();
	}

	// get the shortest (distance) route between two station nodes
	@GetMapping("/route/shortest")
	public Collection<Connection> getShortestPath(@RequestParam String startStation, @RequestParam String endStation) {
		return this.connectionRepository.findShortestPath(startStation, endStation);
	}

	// get a predefined route by id
	@GetMapping("/route/{id}")
	public Collection<Connection> getRouteById(@PathVariable Long id) {
		return this.connectionRepository.findRouteById(id);
	}
	
	@DeleteMapping("/route/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteRouteById(@PathVariable Long id) throws QueryFailedException {
		try {
			this.routeRepository.deleteById(id);
		} catch (Exception e) {
			String errorMessage = "Could not delete route with id " + id.toString();
			throw new QueryFailedException(errorMessage);
		}
	}
}
