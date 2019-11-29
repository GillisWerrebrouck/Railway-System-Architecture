package com.railway.train_service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("train")
public class TrainRestController {

	@Autowired
	private TrainRepository trainRepository;
	
	@GetMapping
	public Iterable<Train> getTrains() {
		return this.trainRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Train> TrainById(@PathVariable("id") String id) {
		Optional <Train> optTrain = trainRepository.findById(id);
		if(optTrain.isPresent()) {
			return new ResponseEntity<>(optTrain.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
}
