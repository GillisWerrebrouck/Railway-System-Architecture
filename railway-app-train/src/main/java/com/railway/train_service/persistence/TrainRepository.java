package com.railway.train_service.persistence;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.railway.train_service.domain.Train;
import com.railway.train_service.domain.TrainType;

public interface TrainRepository extends MongoRepository<Train, String> {
	public Collection<Train> getAllTrainsByType(TrainType trainType);
}
