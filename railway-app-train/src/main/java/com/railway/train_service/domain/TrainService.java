package com.railway.train_service.domain;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.railway.train_service.adapters.messaging.ChangeStatusRequest;
import com.railway.train_service.adapters.messaging.AccidentRequest;
import com.railway.train_service.adapters.messaging.EmergencyRequest;
import com.railway.train_service.adapters.messaging.MaintenanceRequest;
import com.railway.train_service.adapters.messaging.MessageGateway;
import com.railway.train_service.adapters.messaging.TrainOutOfServiceRequest;
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
			if(train.getStatus() == TrainStatus.ACTIVE && isTrainAvailable(schedule, startDateTime, endDateTime)) {
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
					trainRepository.save(train);
					break;
				}
			}
		}
	}
	
	public synchronized void switchTrainReservationsOfTrain(String trainId) {
		Train train = trainRepository.findById(trainId).orElse(null);
		if(train == null) return;
		
		List<ScheduleItem> scheduleItems = train.getScheduleItems()
												.stream()
												.filter(item -> item.getStartDateTime().isAfter(LocalDateTime.now()))
												.collect(Collectors.toList());
		
		Iterator<ScheduleItem> schedule = scheduleItems.iterator();
		
		while(schedule.hasNext()) {
			ScheduleItem scheduleItem = schedule.next();
			// maintenance schedule items can still exist on a train that is inactive
			if (scheduleItem.getReservationType() == ReservationType.MAINTENANCE_RESERVATION) continue;
			
			// the old train can't be reserved because it is still assigned to the current schedule
			Train newTrain = this.reserveTrain(
				scheduleItem.getTimetableId(), 
				scheduleItem.getReservationType(), 
				scheduleItem.getStartDateTime(),
				scheduleItem.getEndDateTime(), 
				train.getType()
			);
			scheduleItems.removeIf(s -> s.getTimetableId() == scheduleItem.getTimetableId());
			train.setScheduleItems(scheduleItems);
			trainRepository.save(train);
			
			TrainOutOfServiceRequest request = new TrainOutOfServiceRequest();
			if(newTrain == null) {
				request.setTrainId(null);
			} else {
				request.setTrainId(newTrain.getId());
			}
			
			request.setTimeTableId(scheduleItem.getTimetableId());
			gateway.notifyTrainOutOfService(request);
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

	public void changeTrainStatus(ChangeStatusRequest request) {
		Train train = trainRepository.findById(request.getTrainId()).orElse(null);
		if(train != null) {
			train.setStatus(request.getStatus());
			if(request.getStatus().equals(TrainStatus.NONACTIVE)) {
				this.switchTrainReservationsOfTrain(train.getId());
			}
			trainRepository.save(train);
		}
	}

	public void requestMaintenanceForAccident(AccidentRequest request) {
		gateway.notifyAccident(request);
		if (request.isEmergencyServiceRequired()) {
			EmergencyRequest r = new EmergencyRequest(request.getTrainId(), request.getAccidentMessage());
			gateway.requestEmergency(r);
		}
	}
}
