package com.railway.train_service;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrainRepository extends MongoRepository<Train, String> {
	
}
