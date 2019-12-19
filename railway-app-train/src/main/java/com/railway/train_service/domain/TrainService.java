package com.railway.train_service.domain;

import java.time.LocalDateTime;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.train_service.adapters.messaging.AccidentRequest;
import com.railway.train_service.adapters.messaging.EmergencyRequest;
import com.railway.train_service.adapters.messaging.MaintenanceRequest;
import com.railway.train_service.adapters.messaging.MessageGateway;
import com.railway.train_service.persistence.TrainRepository;

@Service
public class TrainService {
	TrainRepository trainRepository;
	private MessageGateway gateway;
	
	@Autowired
	public TrainService(TrainRepository trainRepository, MessageGateway gateway) {
		this.trainRepository = trainRepository;
		this.gateway = gateway;
	}
	
	public synchronized Train reserveTrain(Long timetableId, ReservationType reservationType, LocalDateTime startDateTime, LocalDateTime endDateTime, TrainType trainType) {
		Iterable<Train> trains = trainRepository.getAllTrainsByType(trainType);
		Train reservedTrain = null;
		
		for(Train train : trains) {
			Iterable<ScheduleItem> schedule = train.getScheduleItems();
			if(isTrainAvailable(schedule, startDateTime, endDateTime)) {
				reservedTrain = train;
				ScheduleItem scheduleItem = new ScheduleItem(timetableId, reservationType, startDateTime, endDateTime);
				reservedTrain.addScheduleItem(scheduleItem);
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

	public void requestMaintenance(MaintenanceRequest request) {
		gateway.requestMaintenance(request);
	}

	public boolean reserveTrainForMaintenance(ReservationType maintenanceReservation, LocalDateTime startDateTime,
			LocalDateTime endDateTime, String id) {
		Train train = trainRepository.findById(id).orElse(null);
		Train reservedTrain = null;
		
		Iterable<ScheduleItem> schedule = train.getScheduleItems();
		if(isTrainAvailable(schedule, startDateTime, endDateTime)) {
			reservedTrain = train;
			ScheduleItem scheduleItem = new ScheduleItem(null, ReservationType.MAINTENANCE_RESERVATION, startDateTime, endDateTime);
			reservedTrain.addScheduleItem(scheduleItem);
			trainRepository.save(reservedTrain);
			return true;
		}
		
		return false;
	}


	public void requestMaintenanceForAccident(AccidentRequest request) {
		request.setAccidentDate(LocalDateTime.now());
		gateway.notifyAccident(request);
		if (request.isEmergencyServiceRequired()) {
			EmergencyRequest r = new EmergencyRequest(request.getTrainId(), request.getAccidentMessage());
			gateway.requestEmergency(r);
		}
	}
}
