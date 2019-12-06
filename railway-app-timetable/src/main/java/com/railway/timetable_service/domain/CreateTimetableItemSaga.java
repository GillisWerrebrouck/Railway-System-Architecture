package com.railway.timetable_service.domain;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.timetable_service.adapters.messaging.MessageGateway;
import com.railway.timetable_service.adapters.messaging.RouteRequest;

@Service
public class CreateTimetableItemSaga {
	private static Logger logger = LoggerFactory.getLogger(CreateTimetableItemSaga.class);
	private final MessageGateway gateway;
	private final Set<CreateTimetableItemListener> listeners;
	
	@Autowired
	public CreateTimetableItemSaga(MessageGateway gateway) {
		this.gateway = gateway;
		this.listeners = new HashSet<>(5);
	}
	
	public void registerListener(CreateTimetableItemListener listener) {
		this.listeners.add(listener);
	}
	
	public void startCreateTimetableItemSaga(TimetableItem timetableItem) {
		logger.info("[Create Timetable Item Saga] saga initiated");
		
		RouteRequest routeRequest = new RouteRequest(timetableItem.getRouteId(), timetableItem.getId());
		logger.info("[Create Timetable Item Saga] get route command sent");
		gateway.getRoute(routeRequest);
	}
	
	public void onRouteFetched(TimetableItem timetableItem, Long routeId) {
		logger.info("[Create Timetable Item Saga] successfully fetched route");
		timetableItem.setRouteId(routeId);
		this.createTimetableItemComplete(timetableItem);
	}
	
	public void onGetRouteFailed(TimetableItem timetableItem){
		logger.info("[Create Timetable Item Saga] failed to get route");
		this.createTimetableItemFailed(timetableItem);
	}
	
	public void createTimetableItemComplete(TimetableItem timetableItem) {
		logger.info("[Create Timetable Item Saga] successfully created a timetable item");
		this.gateway.emitCreateTimetableItemCompleted(timetableItem);
		this.listeners.forEach(l -> l.onCreateTimetableItemResult(timetableItem));
	}
	
	public void createTimetableItemFailed(TimetableItem timetableItem) {
		logger.info("[Create Timetable Item Saga] failed to create a timetable item");
		this.listeners.forEach(l -> l.onCreateTimetableItemResult(timetableItem));
	}
}
