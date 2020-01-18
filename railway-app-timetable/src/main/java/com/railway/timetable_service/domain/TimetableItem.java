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
	private String reasonForDelay;
	
	private Long routeId;
	private UUID routeRequestId;
	
	private String trainId;
	private TrainType requestedTrainType;
	private UUID trainRequestId;
	private int groupCapacity;
	private int reservedGroupSeats;
	
	private UUID stationsRequestId;
  
	@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
	private UUID trainOperatorRequestId;
	@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
	private UUID trainConductorRequestId;
	private int requestedTrainConductorsAmount;
  
	@Column
	@ElementCollection(targetClass=String.class, fetch=FetchType.EAGER)
	private List<String> staffIds = new ArrayList<String>();
	
	private Status routeStatus;
	private Status trainReservationStatus;
	private Status stationsReservationStatus;
	private Status staffReservationStatus;
	
	@SuppressWarnings("unused")
	private TimetableItem() {}
  
	public TimetableItem(LocalDateTime startDate, LocalDateTime endDateTime, Long routeId, String trainId, TrainType requestedTrainType, List<String> staffIds, int requestedTrainConductorsAmount) {
		this.startDateTime = startDate;
		this.endDateTime = endDateTime;
		this.delay = 0;
		this.reasonForDelay = null;
		this.reservedGroupSeats = 0;
		this.routeId = routeId;
		this.routeRequestId = null;
		this.trainId = trainId;
		this.trainRequestId = null;
		this.groupCapacity = 0;
		this.requestedTrainType = requestedTrainType;
		this.stationsRequestId = null;
		this.trainOperatorRequestId = null;
		this.trainConductorRequestId = null;
		this.requestedTrainConductorsAmount = requestedTrainConductorsAmount;
		this.staffIds = staffIds;
		this.routeStatus = Status.UNKNOWN;
		this.trainReservationStatus = Status.UNKNOWN;
		this.stationsReservationStatus = Status.UNKNOWN;
		this.staffReservationStatus = Status.UNKNOWN;
	}
  
	public TimetableItem(Long routeId, LocalDateTime startDate, TrainType requestedTrainType, int requestedStaffAmount) {
		this(startDate, null, routeId, null, requestedTrainType, new ArrayList<String>(), requestedStaffAmount);
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

	public List<String> getStaffIds() {
		return staffIds;
	}
	
	public UUID getTrainOperatorRequestId() {
		return trainOperatorRequestId;
	}
	
	public void setTrainOperatorRequestId(UUID trainOperatorRequestId) {
		this.trainOperatorRequestId = trainOperatorRequestId;
	}
	
	public UUID getTrainConductorRequestId() {
		return trainConductorRequestId;
	}
	
	public void setTrainConductorRequestId(UUID trainConductorRequestId) {
		this.trainConductorRequestId = trainConductorRequestId;
	}
	
	public int getRequestedTrainConductorsAmount() {
		return requestedTrainConductorsAmount;
	}
	
	public void setRequestedTrainConductorsAmount(int requestedTrainConductorsAmount) {
		this.requestedTrainConductorsAmount = requestedTrainConductorsAmount;
	}
	
	public void setStaffIds(List<String> staffIds) {
		this.staffIds = staffIds;
	}
	
	public void addStaffId(String staffId) {
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
	
	public String getReasonForDelay() {
		return reasonForDelay;
	}

	public void setReasonForDelay(String reasonForDelay) {
		this.reasonForDelay = reasonForDelay;
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
