package com.railway.timetable_service.adapters.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import com.railway.timetable_service.domain.TimetableService;

@Service
public class TimetableEventHandler {
	private final TimetableService timetableItemService;
	
	@Autowired
	public TimetableEventHandler(TimetableService timetableItemService) {
		this.timetableItemService = timetableItemService;
	}
	
	@StreamListener(Channels.ROUTE_FETCHED)
	public void processFetchedRoute(RouteFetchedResponse response) {
		if(response.getRoute().size() != 0) {
			this.timetableItemService.routeFetched(response);
		} else {
			this.timetableItemService.failedToCreateTimetableItem(response);
		}
	}
}
