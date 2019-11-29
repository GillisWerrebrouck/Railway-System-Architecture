package com.railway.route_management_service.adapters.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.railway.route_management_service.domain.Connection;
import com.railway.route_management_service.domain.QueryFailedException;
import com.railway.route_management_service.domain.Station;
import com.railway.route_management_service.persistence.ConnectionRepository;
import com.railway.route_management_service.persistence.StationRepository;

@RestController
@RequestMapping("/network")
public class RouteRestController {
	private final StationRepository stationRepository;
	private final ConnectionRepository connectionRepository;
	
	@Autowired
	public RouteRestController(StationRepository stationRepository, ConnectionRepository connectionRepository) {
		this.stationRepository = stationRepository;
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

	@GetMapping("/connections/{name}")
	public Collection<Connection> getConnectionsByStationName(@PathVariable("name") String name){
		return this.connectionRepository.findConnectionsByStationName(name);
	}

	@GetMapping("/path_between")
	public Collection<Connection> getShortestPath(@RequestParam String startStation, @RequestParam String endStation){
		return this.connectionRepository.findShortestPath(startStation, endStation);
	}

	@GetMapping("/new/{name}")
	public Station createStation(@PathVariable("name") String name){
		return this.stationRepository.createStation(name);
	}

	@PostMapping("/connect")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void connectStations(@RequestBody Connection connection){
		int result = this.connectionRepository.connectStations(connection.getStationX().getName(), connection.getStationY().getName(), connection.getDistance());
		if (result == 0) {
			throw new QueryFailedException("Connection between " + connection.getStationX().getName() + " and " + connection.getStationY().getName() + " could not be created");
		}
	}
}
