package com.railway.train_service.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.train_service.persistence.TrainRepository;

@Service
public class TrainService {
	TrainRepository trainRepository;
	
	@Autowired
	public TrainService(TrainRepository trainRepository) {
		this.trainRepository = trainRepository;
	}
	
	public Train reserveTrain(LocalDateTime startDateTime, LocalDateTime endDateTime, TrainType trainType) {
		Collection<Train> trains = trainRepository.getAllTrainsByType(trainType);
		Train reservedTrain = null;
		
		for(Train train : trains) {
			Collection<ScheduleItem> schedule = train.getScheduleItems();
			boolean isTrainAvailable = true;
			
			for(ScheduleItem scheduleItem : schedule) {
				if(startDateTime.isAfter(scheduleItem.getStartDateTime()) && startDateTime.isBefore(scheduleItem.getEndDateTime())) {
					isTrainAvailable = false;
					break;
				}
			}
			
			if(isTrainAvailable) {
				reservedTrain = train;
				ScheduleItem scheduleItem = new ScheduleItem(startDateTime, endDateTime);
				List<ScheduleItem> scheduleItems = new ArrayList<>();
				scheduleItems.add(scheduleItem);
				reservedTrain.setScheduleItems(scheduleItems);
				trainRepository.save(reservedTrain);
				break;
			}
		}
		
		return reservedTrain;
	}
}
