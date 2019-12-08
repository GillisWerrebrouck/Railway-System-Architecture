package com.railway.station_service.adapters.rest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

import com.railway.station_service.domain.Platform;
import com.railway.station_service.domain.Station;
import com.railway.station_service.domain.exception.BadRequestException;
import com.railway.station_service.persistence.StationRepository;

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
	public ResponseEntity<Station> stationById(@PathVariable UUID id) {
		Optional <Station> optStation = stationRepository.findById(id);
		if(optStation.isPresent()) {
			return new ResponseEntity<>(optStation.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{id}/platform")
	public ResponseEntity<List<Platform>> platformsByStationId(@PathVariable UUID id) {
		List<Platform> optPlatforms = stationRepository.getPlatformsByStationId(id);
		if(optPlatforms != null) {
			return new ResponseEntity<>(optPlatforms, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{id}/platform/{id_pl}")
	public ResponseEntity<Platform> platformByStationIdAndPlatformId(@PathVariable UUID id, @PathVariable Long id_pl) {
		List<Platform> optPlatforms = stationRepository.getPlatformsByStationId(id);
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
	public void postStation (@RequestBody Station s) {
		if (s.getName().compareTo("") == 0) {
			throw new BadRequestException("Missing name attribute for station");
		}
		try {
			stationRepository.save(s);
		}
		catch (Exception e) {
			throw new BadRequestException("Could not update given station : " + e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public void deleteStation(@PathVariable UUID id) {
		try {
			stationRepository.deleteById(id);
		} catch (Exception e) {
			String errorMessage = "Could not delete station with id " + id + " : " + e.getMessage();
			throw new BadRequestException(errorMessage);
		}
	}
	
	@PutMapping("/{id}")
	public void updateStation(@RequestBody Station station, @PathVariable UUID id) throws BadRequestException {

		Optional<Station> stationOptional = stationRepository.findById(id);

		if (!stationOptional.isPresent())
			throw new BadRequestException("Could not find a station for the given ID");

		station.setId(id);
		try {
			stationRepository.save(station);
		}
		catch (Exception e) {
			throw new BadRequestException("Could not save given station : " + e.getMessage());
		}
	}
}
