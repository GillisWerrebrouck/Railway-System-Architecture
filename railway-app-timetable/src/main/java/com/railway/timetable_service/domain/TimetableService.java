package com.railway.timetable_service.domain;

import java.util.Collection;
import java.util.UUID;

import com.railway.timetable_service.adapters.messaging.GroupSeatsRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.timetable_service.adapters.messaging.DelayRequest;
import com.railway.timetable_service.adapters.messaging.Route;
import com.railway.timetable_service.adapters.messaging.RouteFetchedResponse;
import com.railway.timetable_service.adapters.messaging.StaffResponse;
import com.railway.timetable_service.adapters.messaging.StationsResponse;
import com.railway.timetable_service.adapters.messaging.TrainOutOfServiceResponse;
import com.railway.timetable_service.adapters.messaging.TrainReservedResponse;
import com.railway.timetable_service.adapters.messaging.UpdateDelayRequest;
import com.railway.timetable_service.adapters.rest.ScheduleItemResponse;
import com.railway.timetable_service.adapters.rest.SpecificsResponse;
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
			timetableItem.setStaffReservationStatus(Status.DISCARDED);
			timetableItemRepository.save(timetableItem);
			this.createTimetableItemSaga.onReserveStationsFailed(timetableItem);
		}
	}
	
	public synchronized void failedToCreateTimetableItem(TrainReservedResponse trainReservedResponse) {
		TimetableItem timetableItem = timetableItemRepository.findById(trainReservedResponse.getTimetableId()).orElse(null);
		if(timetableItem != null) {
			timetableItem.setTrainReservationStatus(Status.FAILED);
			timetableItem.setStationsReservationStatus(Status.DISCARDED);
			timetableItem.setStaffReservationStatus(Status.DISCARDED);
			timetableItemRepository.save(timetableItem);
			this.createTimetableItemSaga.onReserveTrainFailed(timetableItem);
		}
	}

	public synchronized void failedToCreateTimetableItem(StaffResponse response) {
		TimetableItem timetableItem = timetableItemRepository.findByStaffRequestId(response.getRequestId());
		if(timetableItem != null && timetableItem.getStaffReservationStatus() != Status.DISCARDED) {
			timetableItem.setTrainReservationStatus(Status.DISCARDED);
			timetableItem.setStationsReservationStatus(Status.DISCARDED);
			timetableItem.setStaffReservationStatus(Status.FAILED);
			timetableItemRepository.save(timetableItem);
			this.createTimetableItemSaga.onReserveStaffFailed(timetableItem);
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
		if(timetableItem != null && timetableItem.getStationsRequestId().compareTo(stationsReservedResponse.getRequestId()) == 0 && 
				timetableItem.getStationsReservationStatus() != Status.DISCARDED) {
			timetableItem.setStationsReservationStatus(Status.SUCCESSFUL);
			timetableItemRepository.save(timetableItem);
			this.createTimetableItemSaga.onStationsReserved(timetableItem);
		}
	}
	
	public synchronized void trainReserved(TrainReservedResponse trainReservedResponse) {
		TimetableItem timetableItem = timetableItemRepository.findById(trainReservedResponse.getTimetableId()).orElse(null);
		// check if the response is for the request linked to the given timetableItem
		if(timetableItem != null && timetableItem.getTrainRequestId().compareTo(trainReservedResponse.getRequestId()) == 0 && 
				timetableItem.getTrainReservationStatus() != Status.DISCARDED) {
			timetableItem.setTrainId(trainReservedResponse.getTrainId());
			timetableItem.setGroupCapacity(trainReservedResponse.getGroupCapacity());
			timetableItem.setTrainReservationStatus(Status.SUCCESSFUL);
			timetableItemRepository.save(timetableItem);
			this.createTimetableItemSaga.onTrainReserved(timetableItem);
		}
	}
	
	public synchronized void staffReserved(StaffResponse response) {
		// get the timetable item that contains the requestId		
		TimetableItem timetableItem = timetableItemRepository.findByTrainOperatorRequestId(response.getRequestId());
		if(timetableItem == null) {
			timetableItem = timetableItemRepository.findByTrainConductorRequestId(response.getRequestId());
		}
		
		if(timetableItem == null) {
			return;
		}
		
		// if the response has no staff ids it means that the reservation failed
		if (response.getStaffIds().size() == 0 || 
				timetableItem.getTrainReservationStatus() == Status.FAILED || 
				timetableItem.getStationsReservationStatus() == Status.FAILED || 
				timetableItem.getStaffReservationStatus() == Status.FAILED) {
			this.createTimetableItemSaga.discardTrainReservation(timetableItem.getId());
			this.createTimetableItemSaga.discardStationReservations(timetableItem.getId());
			this.createTimetableItemSaga.discardStaffReservations(timetableItem.getTrainOperatorRequestId());
			this.createTimetableItemSaga.discardStaffReservations(timetableItem.getTrainConductorRequestId());
			return;
		}
		
		// save all staff ids
		for (String staffId : response.getStaffIds()) {
			synchronized (timetableItem) {
				timetableItem = timetableItemRepository.findByStaffRequestId(response.getRequestId());
				
				if(timetableItem != null) {
					timetableItem.addStaffId(staffId);
					timetableItem = timetableItemRepository.save(timetableItem);
				}
			}
		}

		// if all staff is reserved (train operator[1] train conductor(s)[<requested amount>])
		if (timetableItem.getStaffIds().size() == timetableItem.getRequestedTrainConductorsAmount()+1) {
			timetableItem.setStaffReservationStatus(Status.SUCCESSFUL);
			timetableItemRepository.save(timetableItem);
			this.createTimetableItemSaga.onStaffReserved(timetableItem);
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
	
	public SpecificsResponse getSpecifics(Long timetableId) throws Exception {
		TimetableItem timetableItem = timetableItemRepository.findById(timetableId).orElse(null);
		if(timetableItem != null){
			return timetableItemRestAdapter.getSpecifics(timetableItem.getTrainId());
		}
		throw new Exception("Timetable id doesn't exist");
  	}
  
	public synchronized void trainReservationChanged(TrainOutOfServiceResponse trainOutOfServiceResponse) {
		TimetableItem timetableItem = timetableItemRepository.findById(trainOutOfServiceResponse.getTimeTableId()).orElse(null);
		if(timetableItem != null) {
			timetableItem.setTrainId(trainOutOfServiceResponse.getTrainId());
			if(trainOutOfServiceResponse.getTrainId() == null) {
				timetableItem.setTrainReservationStatus(Status.AUTO_RESCHEDULE_FAILED);
			}
			timetableItemRepository.save(timetableItem);
		} else {
			throw new NullPointerException();
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
			throw new NotEnoughGroupSeatsException("Requested amount of group seats (" + toReserveSeats + ") exceeds available amount of group seats (" + (timetableItem.getGroupCapacity()-timetableItem.getReservedGroupSeats()) + ")");
		}
	}

	public synchronized void discardReservedGroupSeats(Long timetableId, int amountOfSeats) {
		TimetableItem timetableItem = timetableItemRepository.findById(timetableId).orElse(null);
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

	public void processExtraDelay(UpdateDelayRequest request) {
		saveTimeTableItemWithDelay(request.getTimetableId(), request.getDelayInMinutes(), null);
	}

	public void processDelay(DelayRequest request) {
		saveTimeTableItemWithDelay(request.getTimetableId(), request.getDelayInMinutes(), request.getReasonForDelay());
	}
	
	public void saveTimeTableItemWithDelay(Long id, int delay,String reason) {
		TimetableItem tItem =  timetableItemRepository.findById(id).get();
		int totalDelay = tItem.getDelay() + delay;
		tItem.setDelay(totalDelay);
		tItem.setReasonForDelay(reason == null ? tItem.getReasonForDelay() : reason);
		timetableItemRepository.save(tItem);
	}
}
