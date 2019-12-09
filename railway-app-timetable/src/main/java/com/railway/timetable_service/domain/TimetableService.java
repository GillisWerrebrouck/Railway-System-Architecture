package com.railway.timetable_service.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.timetable_service.adapters.messaging.RouteFetchedResponse;
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
	
	public synchronized void trainReserved(TrainReservedResponse trainReservedResponse) {
		TimetableItem timetableItem = timetableItemRepository.findById(trainReservedResponse.getTimetableId()).orElse(null);
		timetableItem.setTrainId(trainReservedResponse.getTrainId());
		timetableItem.setTrainReservationStatus(Status.SUCCESSFUL);
		timetableItemRepository.save(timetableItem);
		this.createTimetableItemSaga.onTrainReserved(timetableItem, trainReservedResponse);
	}
	
	public synchronized void failedToCreateTimetableItem(TrainReservedResponse trainReservedResponse) {
		TimetableItem timetableItem = timetableItemRepository.findById(trainReservedResponse.getTimetableId()).orElse(null);
		timetableItem.setTrainReservationStatus(Status.FAILED);
		timetableItemRepository.save(timetableItem);
		this.createTimetableItemSaga.onReserveTrainFailed(timetableItem);
	}
	
	public synchronized void routeFetched(RouteFetchedResponse routeFetchedResponse) {
		TimetableItem timetableItem = timetableItemRepository.findById(routeFetchedResponse.getTimetableId()).orElse(null);
		timetableItem.setRouteStatus(Status.SUCCESSFUL);
		timetableItemRepository.save(timetableItem);
		this.createTimetableItemSaga.onRouteFetched(timetableItem, routeFetchedResponse);
	}
	
	public synchronized void failedToCreateTimetableItem(RouteFetchedResponse routeFetchedResponse) {
		TimetableItem timetableItem = timetableItemRepository.findById(routeFetchedResponse.getTimetableId()).orElse(null);
		timetableItem.setRouteStatus(Status.FAILED);
		timetableItemRepository.save(timetableItem);
		this.createTimetableItemSaga.onGetRouteFailed(timetableItem);
	}
}
