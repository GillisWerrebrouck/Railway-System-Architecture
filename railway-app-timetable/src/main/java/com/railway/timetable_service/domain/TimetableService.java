package com.railway.timetable_service.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.timetable_service.adapters.messaging.RouteFetchedResponse;
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
	
	public void createTimetableItem(TimetableItem timetableItem) {
		synchronized (this.createTimetableItemSaga) {
			this.createTimetableItemSaga.startCreateTimetableItemSaga(timetableItem);
		}
	}
	
	public void registerCreateTimetableListener(CreateTimetableItemListener listener) {
		this.createTimetableItemSaga.registerListener(listener);
	}
	
	public synchronized void routeFetched(RouteFetchedResponse routeFetchedResponse) {
		TimetableItem timetableItem = timetableItemRepository.findById(routeFetchedResponse.getTimeTableId()).orElse(null);
		this.createTimetableItemSaga.onRouteFetched(timetableItem, routeFetchedResponse.getRouteId());
	}
	
	public synchronized void failedToCreateTimetableItem(RouteFetchedResponse routeFetchedResponse) {
		TimetableItem timetableItem = timetableItemRepository.findById(routeFetchedResponse.getRouteId()).orElse(null);
		this.createTimetableItemSaga.onGetRouteFailed(timetableItem);
	}
}
