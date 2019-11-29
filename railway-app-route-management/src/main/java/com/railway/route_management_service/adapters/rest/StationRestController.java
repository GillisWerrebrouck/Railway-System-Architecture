package com.railway.route_management_service.adapters.rest;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.railway.route_management_service.domain.Connection;
import com.railway.route_management_service.domain.QueryFailedException;
import com.railway.route_management_service.domain.Station;
import com.railway.route_management_service.persistence.ConnectionRepository;
import com.railway.route_management_service.persistence.StationRepository;

@RestController
@RequestMapping("/network/station")
public class StationRestController extends RouteRestController{
	public StationRestController(StationRepository stationRepository, ConnectionRepository connectionRepository) {
		super(stationRepository, connectionRepository);
	}

	@GetMapping
	public Iterable<Station> getAllStations(){
		return this.stationRepository.findAll();
	}

	@GetMapping("/{id}")
	public Station getStationById(@PathVariable("id") Long id){
		return this.stationRepository.findById(id).orElse(null);
	}

	@GetMapping("/{name}/connections")
	public Collection<Connection> getConnectionsByStationName(@PathVariable("name") String name){
		return this.connectionRepository.findConnectionsByStationName(name);
	}

	// create a new station node
	@PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void createStation(@RequestBody Station station) throws QueryFailedException {
		try {
			this.stationRepository.createStation(station.getName());
		} catch (Exception e) {
			String errorMessage = "Station with name \"" + station.getName() + "\" could not be created: " + e.getMessage();
			throw new QueryFailedException(errorMessage);
		}
	}
}
