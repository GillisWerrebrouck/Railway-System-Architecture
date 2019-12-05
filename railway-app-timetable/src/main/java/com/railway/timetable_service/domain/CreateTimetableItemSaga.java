package com.railway.timetable_service.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.timetable_service.adapters.messaging.MessageGateway;
import com.railway.timetable_service.adapters.messaging.RouteRequest;

@Service
public class CreateTimetableItemSaga {
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
		RouteRequest routeRequest = new RouteRequest(timetableItem.getRouteId(), timetableItem.getId());
		gateway.getRoute(routeRequest);
	}
	
	public void onRouteFetched(TimetableItem timetableItem, Long routeId) {
		timetableItem.setRouteId(routeId);
		this.createTimetableItemComplete(timetableItem);
	}
	
	public void onGetRouteFailed(TimetableItem timetableItem){
		this.createTimetableItemFailed(timetableItem);
	}
	
	public void createTimetableItemComplete(TimetableItem timetableItem) {
		this.gateway.emitCreateTimetableItemCompleted(timetableItem);
		this.listeners.forEach(l -> l.onCreateTimetableItemResult(timetableItem));
	}
	
	public void createTimetableItemFailed(TimetableItem timetableItem) {
		this.listeners.forEach(l -> l.onCreateTimetableItemResult(timetableItem));
	}
}
