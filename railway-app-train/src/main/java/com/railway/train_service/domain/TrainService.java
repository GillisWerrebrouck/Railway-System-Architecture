package com.railway.train_service.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.train_service.persistence.TrainRepository;

@Service
public class TrainService {
	private static Logger logger = LoggerFactory.getLogger(TrainService.class);
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
			logger.info(train.getId());
			if(isTrainAvailable(schedule, startDateTime, endDateTime)) {
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
	
	private boolean isTrainAvailable(Collection<ScheduleItem> schedule, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		boolean isTrainAvailable = true;
		
		for(ScheduleItem scheduleItem : schedule) {
			if(!(startDateTime.isBefore(scheduleItem.getStartDateTime()) && endDateTime.isBefore(scheduleItem.getStartDateTime()) || 
					startDateTime.isAfter(scheduleItem.getEndDateTime()) && endDateTime.isAfter(scheduleItem.getEndDateTime()))) {
				isTrainAvailable = false;
			}
		}
		
		return isTrainAvailable;
	}
}
