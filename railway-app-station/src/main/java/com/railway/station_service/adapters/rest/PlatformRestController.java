package com.railway.station_service.adapters.rest;

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

import com.railway.station_service.domain.Platform;
import com.railway.station_service.domain.exception.BadRequestException;
import com.railway.station_service.persistence.PlatformRepository;

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
	
	@GetMapping("/{id}")
	public ResponseEntity<Platform> platformById(@PathVariable Long id) {
		Optional<Platform> optPlatform = platformRepository.findById(id);
		if(optPlatform.isPresent()) {
			return new ResponseEntity<>(optPlatform.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void postPlatform (@RequestBody Platform p) throws BadRequestException {
		try {
			platformRepository.save(p);
		}
		catch (Exception e) {
			throw new BadRequestException("Could not update given platform : " + e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public void deletePlatform(@PathVariable Long id) {
		try {
			platformRepository.deleteById(id);
		} catch (Exception e) {
			String errorMessage = "Could not delete platform with id " + id + " : " + e.getMessage();
			throw new BadRequestException(errorMessage);
		}
	}
	
	@PutMapping("/{id}")
	public void updatePlatform(@RequestBody Platform platform, @PathVariable Long id) throws BadRequestException{
		Optional<Platform> platformOptional = platformRepository.findById(id);

		if (platformOptional.isPresent()) {
			platform.setId(id);
			try {
			platformRepository.save(platform);
			}
			catch (Exception e) {
				throw new BadRequestException("Could not save given platform : " + e.getMessage());
			}
		}
		else {
			throw new BadRequestException("Could not find a station for the given ID");
		}
	}
}
