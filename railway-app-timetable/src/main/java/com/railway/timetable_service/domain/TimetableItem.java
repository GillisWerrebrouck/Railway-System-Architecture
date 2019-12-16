package com.railway.timetable_service.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.railway.timetable_service.adapters.messaging.TrainType;

@Entity
public class TimetableItem {
	@Id
	@GeneratedValue
	private Long id;

	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	
	// delay in minutes
	private int delay;
	private int reservedGroupSeats;

	private Long routeId;
	private UUID routeRequestId;
	private String trainId;
	private int groupCapacity;
	private UUID trainRequestId;
	private TrainType requestedTrainType;
	private UUID stationsRequestId;
	
	@Column
    @ElementCollection(targetClass=Long.class, fetch=FetchType.EAGER)
	private List<Long> staffIds = new ArrayList<Long>();
	
	private Status routeStatus;
	private Status trainReservationStatus;
	private Status stationsReservationStatus;
	private Status staffReservationStatus;
	
	@SuppressWarnings("unused")
	private TimetableItem() {}

	public TimetableItem(LocalDateTime startDate, LocalDateTime endDateTime, Long routeId, String trainId, TrainType requestedTrainType, List<Long> staffIds) {
		this.startDateTime = startDate;
		this.endDateTime = endDateTime;
		this.delay = 0;
		this.reservedGroupSeats = 0;
		this.routeId = routeId;
		this.routeRequestId = null;
		this.trainId = trainId;
		this.trainRequestId = null;
		this.groupCapacity = 0;
		this.requestedTrainType = requestedTrainType;
		this.stationsRequestId = null;
		this.staffIds = staffIds;
		this.routeStatus = Status.UNKNOWN;
		this.trainReservationStatus = Status.UNKNOWN;
		this.stationsReservationStatus = Status.UNKNOWN;
		this.staffReservationStatus = Status.UNKNOWN;
	}

	public TimetableItem(Long routeId, LocalDateTime startDate, TrainType requestedTrainType) {
		this(startDate, null, routeId, null, requestedTrainType, new ArrayList<Long>());
	}
	
	public Long getId() {
		return id;
	}
		
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}
	
	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}
	
	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}
	
	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}
	
	public int getDelay() {
		return delay;
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public Long getRouteId() {
		return routeId;
	}
	
	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
	
	public UUID getRouteRequestId() {
		return routeRequestId;
	}
	
	public void setRouteRequestId(UUID routeRequestId) {
		this.routeRequestId = routeRequestId;
	}
	
	public String getTrainId() {
		return trainId;
	}
	
	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}
	
	public UUID getTrainRequestId() {
		return trainRequestId;
	}
	
	public void setTrainRequestId(UUID trainRequestId) {
		this.trainRequestId = trainRequestId;
	}
	
	public TrainType getRequestedTrainType() {
		return requestedTrainType;
	}
	
	public void setRequestedTrainType(TrainType requestedTrainType) {
		this.requestedTrainType = requestedTrainType;
	}
	
	public UUID getStationsRequestId() {
		return stationsRequestId;
	}
	
	public void setStationsRequestId(UUID stationsRequestId) {
		this.stationsRequestId = stationsRequestId;
	}

	public List<Long> getStaffIds() {
		return staffIds;
	}
	
	public void setStaffIds(List<Long> staffIds) {
		this.staffIds = staffIds;
	}
	
	public void addStaffIds(Long staffId) {
		this.staffIds.add(staffId);
	}
	
	public Status getRouteStatus() {
		return routeStatus;
	}
	
	public void setRouteStatus(Status routeStatus) {
		this.routeStatus = routeStatus;
	}
	
	public Status getTrainReservationStatus() {
		return trainReservationStatus;
	}
	
	public void setTrainReservationStatus(Status trainReservationStatus) {
		this.trainReservationStatus = trainReservationStatus;
	}
	
	public Status getStationsReservationStatus() {
		return stationsReservationStatus;
	}
	
	public void setStationsReservationStatus(Status stationsReservationStatus) {
		this.stationsReservationStatus = stationsReservationStatus;
	}
	
	public Status getStaffReservationStatus() {
		return staffReservationStatus;
	}
	
	public void setStaffReservationStatus(Status staffReservationStatus) {
		this.staffReservationStatus = staffReservationStatus;
	}

	public int getGroupCapacity() {
		return groupCapacity;
	}

	public void setGroupCapacity(int groupCapacity) {
		this.groupCapacity = groupCapacity;
	}

	public int getReservedGroupSeats() {
		return reservedGroupSeats;
	}

	public void setReservedGroupSeats(int reservedGroupSeats) {
		this.reservedGroupSeats = reservedGroupSeats;
	}

	@Override
	public String toString() {
		return "Route " + this.routeId + ": " + this.startDateTime.toString() + " - " + this.endDateTime.toString();
	}
}
