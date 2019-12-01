package com.railway.station_service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@GetMapping("/{id}/platforms")
	public ResponseEntity<List<Platform>> platformsByStationId(@PathVariable int id) {
		List<Platform> optPlatforms = stationRepository.getPlatforms((long)id);
		if(optPlatforms != null) {
			return new ResponseEntity<>(optPlatforms, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		
	}
	
	@GetMapping("/{id}/platforms/{id_pl}")
	public ResponseEntity<Platform> platformByStationIdAndPlatformId(@PathVariable int id, @PathVariable int id_pl) {
		List<Platform> optPlatforms = stationRepository.getPlatforms((long)id);
		if(optPlatforms != null) {
			for (int i = 0; i < optPlatforms.size(); i++) {
				if(optPlatforms.get(i).getId() == id_pl) {
					return new ResponseEntity<>(optPlatforms.get(i), HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Station postStation (@RequestBody Station s) {
		return stationRepository.save(s);
	}
	
	@DeleteMapping("/{id}")
	public void deleteStation(@PathVariable int id) {
		stationRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Station> updateStation(@RequestBody Station station, @PathVariable int id) {

		Optional<Station> stationOptional = stationRepository.findById(id);

		if (!stationOptional.isPresent())
			return ResponseEntity.notFound().build();

		station.setId((long) id);
		
		stationRepository.save(station);

		return ResponseEntity.noContent().build();
	}

}
