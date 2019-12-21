package com.railway.timetable_service.domain;

import java.util.Collection;
import java.util.UUID;

import com.railway.timetable_service.adapters.messaging.GroupSeatsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.timetable_service.adapters.messaging.Route;
import com.railway.timetable_service.adapters.messaging.RouteFetchedResponse;
import com.railway.timetable_service.adapters.messaging.StationsResponse;
import com.railway.timetable_service.adapters.messaging.TrainReservedResponse;
import com.railway.timetable_service.adapters.rest.ScheduleItemResponse;
import com.railway.timetable_service.adapters.rest.TimetableItemRestAdapter;
import com.railway.timetable_service.persistence.TimetableItemRepository;

@Service
public class TimetableService {	
	private final CreateTimetableItemSaga createTimetableItemSaga;
	private final TimetableItemRepository timetableItemRepository;
	private final TimetableItemRestAdapter timetableItemRestAdapter;
	
	@Autowired
	public TimetableService(CreateTimetableItemSaga createTimetableItemSaga, TimetableItemRepository timetableItemRepository, TimetableItemRestAdapter timetableItemRestAdapter) {
		this.createTimetableItemSaga = createTimetableItemSaga;
		this.timetableItemRepository = timetableItemRepository;
		this.timetableItemRestAdapter = timetableItemRestAdapter;
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
	
	public String getRouteName(Long routeId) {
		return timetableItemRestAdapter.getRouteName(routeId);
	}
	
	public Collection<Route> getRoutes(UUID startStationId, UUID endStationId) {
		return timetableItemRestAdapter.getRoutes(startStationId, endStationId);
	}
	
	public Collection<ScheduleItemResponse> getStationByTimetableItemId(Long timetableId) {
		return timetableItemRestAdapter.getStationsByTimetableItemId(timetableId);
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
			throw new NotEnoughGroupSeatsException("Requested amount of group seats (" + toReserveSeats + ") exceeds available amount of group seats (" + (timetableItem.getGroupCapacity()-timetableItem.getReservedGroupSeats()) + ")");
		}
	}

	public synchronized void discardReservedGroupSeats(Long timeTableId, int amountOfSeats) {
		TimetableItem timetableItem = timetableItemRepository.findById(timeTableId).orElse(null);
		if(timetableItem != null){
			timetableItem.setReservedGroupSeats(timetableItem.getReservedGroupSeats() - amountOfSeats);
			timetableItemRepository.save(timetableItem);
		}
	}
}
