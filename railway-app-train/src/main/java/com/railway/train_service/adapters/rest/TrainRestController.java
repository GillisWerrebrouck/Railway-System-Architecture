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

import com.railway.train_service.adapters.messaging.MaintenanceRequest;
import com.railway.train_service.adapters.messaging.MaintenanceResponse;
import com.railway.train_service.domain.Train;
import com.railway.train_service.domain.TrainService;
import com.railway.train_service.persistence.TrainRepository;

@RestController
@RequestMapping("/train")
public class TrainRestController {
	private TrainRepository trainRepository;
	private TrainService trainService;
	
	@Autowired
	public TrainRestController(TrainRepository trainRepository, TrainService trainService) {
		this.trainRepository = trainRepository;
		this.trainService = trainService;
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

	@PostMapping("/{id}/maintenance")
	public MaintenanceResponse notifyMaintenance(@PathVariable String id, @RequestBody MaintenanceRequest request) {
		Train train = trainRepository.findById(id).orElse(null);
		MaintenanceResponse response;
		
		if(train != null) {
			trainService.requestMaintenance(request);
			response = new MaintenanceResponse("Successfully fetched train and sent maintenance request");
		} else {
			response = new MaintenanceResponse("Failed to fetch train, maintenance request aborted");
		}
		return response;
	}
	
	@DeleteMapping("/{id}")
	void deleteTrain(@PathVariable String id) {
		trainRepository.deleteById(id);
	}
}
