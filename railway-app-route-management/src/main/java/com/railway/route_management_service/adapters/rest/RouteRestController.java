package com.railway.route_management_service.adapters.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.railway.route_management_service.domain.Connection;
import com.railway.route_management_service.domain.Route;
import com.railway.route_management_service.domain.Station;
import com.railway.route_management_service.persistence.ConnectionRepository;
import com.railway.route_management_service.persistence.RouteRepository;
import com.railway.route_management_service.persistence.StationRepository;

@RestController
@RequestMapping("/network")
public class RouteRestController {
	private final StationRepository stationRepository;
	private final RouteRepository routeRepository;
	private final ConnectionRepository connectionRepository;
	
	@Autowired
	public RouteRestController(StationRepository stationRepository, RouteRepository routeRepository, ConnectionRepository connectionRepository) {
		this.stationRepository = stationRepository;
		this.routeRepository = routeRepository;
		this.connectionRepository = connectionRepository;
	}

	@GetMapping
	public Iterable<Station> getRailwayNetwork(){
		return this.stationRepository.findAll();
	}

	@GetMapping("/all")
	public Collection<Station> getAllStations(){
		return this.stationRepository.findAllStations();
	}

	@GetMapping("/path_between")
	public Collection<Route> getShortestPath(@RequestParam String startStation, @RequestParam String endStation){
		Collection<Route> routes = this.routeRepository.findShortestPath(startStation, endStation);
		return routes;
	}

	@GetMapping("/new/{name}")
	public Station createStation(@PathVariable("name") String name){
		return this.stationRepository.createStation(name);
	}

	@PostMapping("/connect")
	public Connection connectStations(@RequestBody Connection connection){
		Connection c = this.connectionRepository.connectStations(connection.getStationX().getName(), connection.getStationY().getName(), connection.getDistance());
		return c;
	}
}
