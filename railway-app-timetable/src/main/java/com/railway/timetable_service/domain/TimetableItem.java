package com.railway.timetable_service.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	
	private Long routeId;
	private Status routeStatus; 
	private String trainId;
	private TrainType requestedTrainType;
	private Status trainReservationStatus;
	@Column
    @ElementCollection(targetClass=Long.class, fetch=FetchType.EAGER)
	private List<Long> staffIds = new ArrayList<Long>();
	private Status staffReservationStatus;
	
	@SuppressWarnings("unused")
	private TimetableItem() {}

	public TimetableItem(LocalDateTime startDate, LocalDateTime endDateTime, Long routeId, String trainId, TrainType requestedTrainType, List<Long> staffIds) {
		this.startDateTime = startDate;
		this.endDateTime = endDateTime;
		this.delay = 0;
		this.routeId = routeId;
		this.routeStatus = Status.UNKNOWN;
		this.trainId = trainId;
		this.requestedTrainType = requestedTrainType;
		this.trainReservationStatus = Status.UNKNOWN;
		this.staffIds = staffIds;
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
	
	public Status getRouteStatus() {
		return routeStatus;
	}
	
	public void setRouteStatus(Status routeStatus) {
		this.routeStatus = routeStatus;
	}
	
	public String getTrainId() {
		return trainId;
	}
	
	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}
	
	public TrainType getRequestedTrainType() {
		return requestedTrainType;
	}
	
	public void setRequestedTrainType(TrainType requestedTrainType) {
		this.requestedTrainType = requestedTrainType;
	}
	
	public Status getTrainReservationStatus() {
		return trainReservationStatus;
	}
	
	public void setTrainReservationStatus(Status trainReservationStatus) {
		this.trainReservationStatus = trainReservationStatus;
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
	
	public Status getStaffReservationStatus() {
		return staffReservationStatus;
	}
	
	public void setStaffReservationStatus(Status staffReservationStatus) {
		this.staffReservationStatus = staffReservationStatus;
	}
	
	@Override
	public String toString() {
		return "Route " + this.routeId + ": " + this.startDateTime.toString() + " - " + this.endDateTime.toString();
	}
}
