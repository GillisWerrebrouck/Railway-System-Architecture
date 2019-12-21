package com.railway.timetable_service.domain;

import com.railway.timetable_service.adapters.messaging.GroupSeatsRequest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.timetable_service.adapters.messaging.RouteFetchedResponse;
import com.railway.timetable_service.adapters.messaging.RouteUsageRequest;
import com.railway.timetable_service.adapters.messaging.RouteUsageResponse;
import com.railway.timetable_service.adapters.messaging.StationsResponse;
import com.railway.timetable_service.adapters.messaging.TrainReservedResponse;
import com.railway.timetable_service.persistence.TimetableItemRepository;

@Service
public class TimetableService {	
	private final CreateTimetableItemSaga createTimetableItemSaga;
	private final TimetableItemRepository timetableItemRepository;
	
	@Autowired
	public TimetableService(CreateTimetableItemSaga createTimetableItemSaga, TimetableItemRepository timetableItemRepository) {
		this.createTimetableItemSaga = createTimetableItemSaga;
		this.timetableItemRepository = timetableItemRepository;
	}
	
	public void createTimetableItem(TimetableItem timetableItem, TimetableRequest timetableRequest) {
		synchronized (this.createTimetableItemSaga) {
			timetableItem.setRouteStatus(Status.AWAITING_INITIATION);
			timetableItem.setTrainReservationStatus(Status.AWAITING_INITIATION);
			timetableItem.setStaffReservationStatus(Status.AWAITING_INITIATION);
			timetableItemRepository.save(timetableItem);
			this.createTimetableItemSaga.startCreateTimetableItemSaga(timetableItem, timetableRequest);
		}
	}
	
	public void registerCreateTimetableListener(CreateTimetableItemListener listener) {
		this.createTimetableItemSaga.registerListener(listener);
	}
	
	public synchronized void failedToCreateTimetableItem(RouteFetchedResponse routeFetchedResponse) {
		TimetableItem timetableItem = timetableItemRepository.findById(routeFetchedResponse.getTimetableId()).orElse(null);
		if(timetableItem != null) {
			timetableItem.setRouteStatus(Status.FAILED);
			timetableItemRepository.save(timetableItem);
			this.createTimetableItemSaga.onGetRouteFailed(timetableItem);
		}
	}

	public synchronized void failedToCreateTimetableItem(StationsResponse stationsReservedResponse) {
		TimetableItem timetableItem = timetableItemRepository.findById(stationsReservedResponse.getTimetableId()).orElse(null);
		if(timetableItem != null) {
			timetableItem.setStationsReservationStatus(Status.FAILED);
			timetableItem.setTrainReservationStatus(Status.DISCARDED);
			timetableItemRepository.save(timetableItem);
			this.createTimetableItemSaga.onReserveStationsFailed(timetableItem);
		}
	}
	
	public synchronized void failedToCreateTimetableItem(TrainReservedResponse trainReservedResponse) {
		TimetableItem timetableItem = timetableItemRepository.findById(trainReservedResponse.getTimetableId()).orElse(null);
		if(timetableItem != null) {
			timetableItem.setTrainReservationStatus(Status.FAILED);
			timetableItem.setStationsReservationStatus(Status.DISCARDED);
			timetableItemRepository.save(timetableItem);
			this.createTimetableItemSaga.onReserveTrainFailed(timetableItem);
		}
	}
	
	public synchronized void routeFetched(RouteFetchedResponse routeFetchedResponse) {
		TimetableItem timetableItem = timetableItemRepository.findById(routeFetchedResponse.getTimetableId()).orElse(null);
		// check if the response is for the request linked to the given timetableItem
		if(timetableItem != null && timetableItem.getRouteRequestId().compareTo(routeFetchedResponse.getRequestId()) == 0) {
			timetableItem.setRouteStatus(Status.SUCCESSFUL);
			timetableItemRepository.save(timetableItem);
			this.createTimetableItemSaga.onRouteFetched(timetableItem, routeFetchedResponse);
		}
	}

	public synchronized void stationsReserved(StationsResponse stationsReservedResponse) {
		TimetableItem timetableItem = timetableItemRepository.findById(stationsReservedResponse.getTimetableId()).orElse(null);
		// check if the response is for the request linked to the given timetableItem
		if(timetableItem != null && timetableItem.getStationsRequestId().compareTo(stationsReservedResponse.getRequestId()) == 0) {
			timetableItem.setStationsReservationStatus(Status.SUCCESSFUL);
			timetableItemRepository.save(timetableItem);
			this.createTimetableItemSaga.onStationsReserved(timetableItem);
		}
		
		if(timetableItem == null) {
			this.createTimetableItemSaga.discardStationReservations(stationsReservedResponse.getTimetableId());
		}
	}
	
	public synchronized void trainReserved(TrainReservedResponse trainReservedResponse) {
		TimetableItem timetableItem = timetableItemRepository.findById(trainReservedResponse.getTimetableId()).orElse(null);
		// check if the response is for the request linked to the given timetableItem
		if(timetableItem != null && timetableItem.getTrainRequestId().compareTo(trainReservedResponse.getRequestId()) == 0) {
			timetableItem.setTrainId(trainReservedResponse.getTrainId());
			timetableItem.setGroupCapacity(trainReservedResponse.getGroupCapacity());
			timetableItem.setTrainReservationStatus(Status.SUCCESSFUL);
			timetableItemRepository.save(timetableItem);
			this.createTimetableItemSaga.onTrainReserved(timetableItem);
		}
		
		if(timetableItem != null) {
			this.createTimetableItemSaga.discardTrainReservation(trainReservedResponse.getTimetableId());
		}
	}

	public void reserveGroupSeats(GroupSeatsRequest groupSeatsRequest) throws NotEnoughGroupSeatsException {
		TimetableItem timetableItem = timetableItemRepository.findById(groupSeatsRequest.getTimeTableId()).orElse(null);
		if(timetableItem != null){
			reserveGroupSeats(timetableItem, groupSeatsRequest.getAmountOfSeats());
		}else{
			throw new NullPointerException();
		}
	}

	private synchronized void reserveGroupSeats(TimetableItem timetableItem, int toReserveSeats) throws NotEnoughGroupSeatsException {
		if(timetableItem.getGroupCapacity() - timetableItem.getReservedGroupSeats() >= toReserveSeats){
			timetableItem.setReservedGroupSeats(timetableItem.getReservedGroupSeats() + toReserveSeats);
			timetableItemRepository.save(timetableItem);
		}else{
			throw new NotEnoughGroupSeatsException();
		}
	}

	public synchronized void discardReservedGroupSeats(Long timeTableId, int amountOfSeats) {
		TimetableItem timetableItem = timetableItemRepository.findById(timeTableId).orElse(null);
		if(timetableItem != null){
			timetableItem.setReservedGroupSeats(timetableItem.getReservedGroupSeats() - amountOfSeats);
			timetableItemRepository.save(timetableItem);
		}
	}

	public boolean areRoutesUsed(List<Long> routeIds) {
		boolean areUsed = false;
		for(Long routeId : routeIds) {
			if (timetableItemRepository.findByRouteIdInFromNow(routeId).size() > 0) {
				areUsed = true;
			}
		}
		return areUsed;
	}
}
