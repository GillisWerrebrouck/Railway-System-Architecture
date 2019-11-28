package com.railway.station_service;


import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platform")
public class PlatformRestController {

	private final PlatformRepository platformRepository;

	@Autowired
	public PlatformRestController(PlatformRepository platformRepository) {
		this.platformRepository = platformRepository;
	}

	@GetMapping
	public Iterable<Platform> getPlatforms(){
		return this.platformRepository.findAll();
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Platform postPlatform (@RequestBody Platform s) {
		return platformRepository.save(s);
	}
	/*
	@GetMapping("/{id}")
	public ResponseEntity<Station> stationById(@PathVariable int id) {
		Optional <Station> optStation = stationRepository.findById(id);
		if(optStation.isPresent()) {
			return new ResponseEntity<>(optStation.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		
	}
	
	
	
/*
	@GetMapping("/all")
	public Collection<Station> getAllStations(){
		return this.stationRepository.findAllStations();
	}
*/
}
