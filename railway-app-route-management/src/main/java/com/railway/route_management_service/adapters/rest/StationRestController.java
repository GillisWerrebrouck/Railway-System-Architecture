package com.railway.route_management_service.adapters.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railway.route_management_service.domain.Station;
import com.railway.route_management_service.persistence.StationRepository;

@RestController
@RequestMapping("/station")
public class StationRestController {
	private final StationRepository stationRepository;
	
	@Autowired
	public StationRestController(StationRepository stationRepository) {
		this.stationRepository = stationRepository;
	}

	@GetMapping
	public Iterable<Station> getRailwayNetwork(){
		return this.stationRepository.findAll();
	}

	@GetMapping("/all")
	public Collection<Station> getAllStations(){
		return this.stationRepository.findAllStations();
	}
}
