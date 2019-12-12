package com.railway.train_service.adapters.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railway.train_service.domain.Train;
import com.railway.train_service.persistence.TrainRepository;

@RestController
@RequestMapping("/train")
public class TrainRestController {
	private TrainRepository trainRepository;
	
	@Autowired
	public TrainRestController(TrainRepository trainRepository) {
		this.trainRepository = trainRepository;
	}
	
	@GetMapping
	public Iterable<Train> getTrains() {
		return this.trainRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Train> TrainById(@PathVariable("id") String id) {
		Train train = trainRepository.findById(id).orElse(null);
		if(train != null) {
			return new ResponseEntity<>(train, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping()
	public Train addTrain(@RequestBody Train train) {
		this.trainRepository.save(train);
		return train;
	}
	
	@DeleteMapping("/{id}")
	void deleteTrain(@PathVariable String id) {
		trainRepository.deleteById(id);
	}
}
