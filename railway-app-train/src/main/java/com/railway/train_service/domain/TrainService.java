package com.railway.train_service.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
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
	
	public synchronized Train reserveTrain(Long timetableId, LocalDateTime startDateTime, LocalDateTime endDateTime, TrainType trainType) {
		Iterable<Train> trains = trainRepository.getAllTrainsByType(trainType);
		Train reservedTrain = null;
		
		for(Train train : trains) {
			Iterable<ScheduleItem> schedule = train.getScheduleItems();
			if(isTrainAvailable(schedule, startDateTime, endDateTime)) {
				reservedTrain = train;
				ScheduleItem scheduleItem = new ScheduleItem(timetableId, startDateTime, endDateTime);
				List<ScheduleItem> scheduleItems = new ArrayList<>();
				scheduleItems.add(scheduleItem);
				reservedTrain.setScheduleItems(scheduleItems);
				trainRepository.save(reservedTrain);
				break;
			}
		}
		
		return reservedTrain;
	}
	
	private boolean isTrainAvailable(Iterable<ScheduleItem> schedule, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		boolean isTrainAvailable = true;
		
		for(ScheduleItem scheduleItem : schedule) {
			if(!(startDateTime.isBefore(scheduleItem.getStartDateTime()) && endDateTime.isBefore(scheduleItem.getStartDateTime()) || 
					startDateTime.isAfter(scheduleItem.getEndDateTime()) && endDateTime.isAfter(scheduleItem.getEndDateTime()))) {
				isTrainAvailable = false;
			}
		}
		
		return isTrainAvailable;
	}
	
	public synchronized void discardTrainReservation(Long timetableId) {
		Iterable<Train> trains = trainRepository.findAll();
		
		for(Train train : trains) {
			Iterator<ScheduleItem> schedule = train.getScheduleItems().iterator();
			while(schedule.hasNext()) {
				ScheduleItem scheduleItem = schedule.next();
				if(scheduleItem.getTimetableId() == timetableId) {
					schedule.remove();
					break;
				}
			}
		}
	}
}
