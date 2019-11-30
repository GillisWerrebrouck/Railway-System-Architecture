package com.railway.station_service;


import java.util.Collection;
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
	public ResponseEntity<Platform> platformById(@PathVariable int id) {
		Optional <Platform> optPlatform = platformRepository.findById(id);
		if(optPlatform.isPresent()) {
			return new ResponseEntity<>(optPlatform.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Platform postPlatform (@RequestBody Platform p) {
		return platformRepository.save(p);
	}
	
	@DeleteMapping("/{id}")
	public void deletePlatform(@PathVariable int id) {
		platformRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Platform> updatePlatform(@RequestBody Platform platform, @PathVariable int id) {

		Optional<Platform> platformOptional = platformRepository.findById(id);

		if (!platformOptional.isPresent())
			return ResponseEntity.notFound().build();

		platform.setId(id);
		
		platformRepository.save(platform);

		return ResponseEntity.noContent().build();
	}


}
