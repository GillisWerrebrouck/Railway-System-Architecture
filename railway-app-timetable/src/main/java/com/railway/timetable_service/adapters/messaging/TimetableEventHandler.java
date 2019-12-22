package com.railway.timetable_service.adapters.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import com.railway.timetable_service.domain.TimetableService;

@Service
public class TimetableEventHandler {
	private static Logger logger = LoggerFactory.getLogger(TimetableEventHandler.class);
	private final TimetableService timetableItemService;
	
	@Autowired
	public TimetableEventHandler(TimetableService timetableItemService) {
		this.timetableItemService = timetableItemService;
	}
	
	@StreamListener(Channels.ROUTE_FETCHED)
	public void processFetchedRoute(RouteFetchedResponse response) {
		if(response.getRouteConnections().size() != 0) {
			logger.info("[Timetable Event Handler] successfully fetched route");
			this.timetableItemService.routeFetched(response);
		} else {
			logger.info("[Timetable Event Handler] failed to fetch route");
			this.timetableItemService.failedToCreateTimetableItem(response);
		}
	}
	
	@StreamListener(Channels.STATIONS_RESERVED)
	public void processStationsReservedResponse(StationsResponse response) {
		if(response.wasReservationSuccessful()) {
			logger.info("[Timetable Event Handler] successfully reserved stations on route");
			this.timetableItemService.stationsReserved(response);
		} else {
			logger.info("[Timetable Event Handler] failed to reserve stations on route");
			this.timetableItemService.failedToCreateTimetableItem(response);
		}
	}
	
	@StreamListener(Channels.TRAIN_RESERVED)
	public void processTrainReservedResponse(TrainReservedResponse response) {
		if(response.getTrainId() != null) {
			logger.info("[Timetable Event Handler] successfully reserved train");
			this.timetableItemService.trainReserved(response);
		} else {
			logger.info("[Timetable Event Handler] failed to reserve train");
			this.timetableItemService.failedToCreateTimetableItem(response);
		}
	}
  
	@StreamListener(Channels.STAFF_RESERVED)
	public void processStaffReservedResponse(StaffResponse response) {
		if(response.getStaffIds().size() != 0) {
			logger.info("[Timetable Event Handler] successfully reserved staff");
			this.timetableItemService.staffReserved(response);
		} else {
			logger.info("[Timetable Event Handler] failed to reserve staff");
			this.timetableItemService.failedToCreateTimetableItem(response);
		}
	}
  
	@StreamListener(Channels.NOTIFY_TRAIN_OUT_OF_SERVICE)
	public void processTrainOutOfService(TrainOutOfServiceResponse response) {
		if(response.getTimeTableId() != null) {
			logger.info("[Timetable Event Handler] successfully rescheduled train");
			this.timetableItemService.trainReservationChanged(response);
		} else {
			logger.info("[Timetable Event Handler] failed to reserve new train");
			throw new NullPointerException("No Timetable Id was specified.");
		}
	}
}
