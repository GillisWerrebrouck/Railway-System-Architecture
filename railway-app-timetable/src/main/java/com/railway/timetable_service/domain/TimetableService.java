package com.railway.timetable_service.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.timetable_service.adapters.messaging.MessageGateway;
import com.railway.timetable_service.adapters.messaging.RouteFetchedResponse;
import com.railway.timetable_service.persistence.TimetableItemRepository;

@Service
public class TimetableService {
	private static Logger logger = LoggerFactory.getLogger(TimetableService.class);
	
	private final MessageGateway gateway;
	private final CreateTimetableItemSaga createTimetableItemSaga;
	private final TimetableItemRepository timetableItemRepository;
	
	@Autowired
	public TimetableService(MessageGateway gateway, CreateTimetableItemSaga createTimetableItemSaga, TimetableItemRepository timetableItemRepository) {
		this.gateway = gateway;
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
		logger.info("TEST!!!!!!!!!!!!!!!!!!!!!");
		TimetableItem timetableItem = timetableItemRepository.findById(routeFetchedResponse.getTimeTableId()).orElse(null);
		logger.info("TEST 22222222222222222!!!!!!!!!!!!!!!!!!!!!");
		this.createTimetableItemSaga.onRouteFetched(timetableItem, routeFetchedResponse.getRouteId());
//		this.hospitalStayRepository.save(hospitalStay);
	}
	
	public synchronized void failedToCreateTimetableItem(RouteFetchedResponse routeFetchedResponse) {
//		TimetableItem timetableItem = timetableItemRepository.findById(routeFetchedResponse.).orElse(null);
//		this.createTimetableItemSaga.onGetRouteFailed(timetableItem);
	}
}
