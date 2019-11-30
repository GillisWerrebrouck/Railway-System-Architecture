package com.railway.station_service;


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
@RequestMapping("/station")
public class StationRestController {

	private final StationRepository stationRepository;

	@Autowired
	public StationRestController(StationRepository stationRepository) {
		this.stationRepository = stationRepository;
	}

	@GetMapping
	public Iterable<Station> getStations(){
		return this.stationRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Station> stationById(@PathVariable int id) {
		Optional <Station> optStation = stationRepository.findById(id);
		if(optStation.isPresent()) {
			return new ResponseEntity<>(optStation.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Station postStation (@RequestBody Station s) {
		return stationRepository.save(s);
	}
	

}
