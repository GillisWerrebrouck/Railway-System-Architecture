package com.railway.train_service.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.railway.train_service.domain.Train;

public interface TrainRepository extends MongoRepository<Train, String> {
	
}
