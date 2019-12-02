package com.railway.route_management_service.adapters.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.railway.route_management_service.domain.Connection;
import com.railway.route_management_service.domain.Route;
import com.railway.route_management_service.domain.RouteConnection;
import com.railway.route_management_service.domain.exception.BadRequestException;
import com.railway.route_management_service.domain.exception.QueryFailedException;
import com.railway.route_management_service.persistence.ConnectionRepository;
import com.railway.route_management_service.persistence.RouteConnectionRepository;
import com.railway.route_management_service.persistence.RouteRepository;
import com.railway.route_management_service.persistence.StationRepository;

@RestController
@RequestMapping("/route")
public class RouteRestController {
	protected final StationRepository stationRepository;
	protected final ConnectionRepository connectionRepository;
	protected final RouteRepository routeRepository;
	protected final RouteConnectionRepository routeConnectionRepository;
	
	@Autowired
	public RouteRestController(StationRepository stationRepository, ConnectionRepository connectionRepository, RouteRepository routeRepository, RouteConnectionRepository routeConnectionRepository) {
		this.stationRepository = stationRepository;
		this.connectionRepository = connectionRepository;
		this.routeRepository = routeRepository;
		this.routeConnectionRepository = routeConnectionRepository;
	}
	
	@GetMapping
	public Iterable<Route> getAllRoutes() {
		return this.routeRepository.findAll();
	}

	// get the shortest (distance) route between two station nodes
	@GetMapping("/shortest")
	public Collection<Connection> getShortestPath(@RequestParam String startStation, @RequestParam String endStation) {
		return this.connectionRepository.findShortestPath(startStation, endStation);
	}

	// get a predefined route by id
	@GetMapping("/{id}")
	public Collection<Connection> getRouteById(@PathVariable Long id) {
		return this.connectionRepository.findRouteById(id);
	}
	
	// create a new route node
	@PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void createRoute(@RequestBody Route route) throws QueryFailedException {
		try {
			if (route.getName().compareTo("") == 0) {
				throw new Exception("Missing name attribute for route node");
			}
			this.routeRepository.save(route);
		} catch (Exception e) {
			String errorMessage = "Route with name \"" + route.getName() + "\" could not be created: " + e.getMessage();
			throw new QueryFailedException(errorMessage);
		}
	}

	@PutMapping("/route")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updateRoute(@RequestBody Route route) throws BadRequestException, QueryFailedException {
		try {
			if (route.getName().compareTo("") == 0) {
				throw new BadRequestException("Missing name attribute for route node");
			}
			this.routeRepository.save(route);
		} catch (Exception e) {
			String errorMessage = "Route with name \"" + route.getName() + "\" could not be created: " + e.getMessage();
			throw new QueryFailedException(errorMessage);
		}
	}
	
	// create a new route-station relationship
	@PostMapping("/route/{id}/part")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void createRoutePart(@RequestBody RouteConnection routeConnection, @PathVariable Long id) throws QueryFailedException {
		try {
			this.routeConnectionRepository.createRouteStationRelationship(id, routeConnection.getStation().getId(), routeConnection.getConnectionId());
		} catch (Exception e) {
			String errorMessage = "Route-station relationship between route \"" + id + "\" and station \""
								  + routeConnection.getStation().getId() +"\" could not be created: " + e.getMessage();
			throw new QueryFailedException(errorMessage);
		}
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
	
	@DeleteMapping("/route/part/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteRouteStationRelationshipById(@PathVariable Long id) throws QueryFailedException {
		try {
			this.routeRepository.deleteRouteStationRelationshipById(id);
		} catch (Exception e) {
			String errorMessage = "Could not delete route part with id " + id.toString();
			throw new QueryFailedException(errorMessage);
		}
	}
}
