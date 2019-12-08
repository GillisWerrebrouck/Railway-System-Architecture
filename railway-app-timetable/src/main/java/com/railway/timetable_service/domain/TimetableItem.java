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
	private Long trainId;
	@Column
    @ElementCollection(targetClass=Long.class, fetch=FetchType.EAGER)
	private List<Long> staffIds = new ArrayList<Long>();
	
	@SuppressWarnings("unused")
	private TimetableItem() {}

	public TimetableItem(LocalDateTime startDate, LocalDateTime endDateTime, Long routeId, Long trainId, List<Long> staffIds) {
		this.startDateTime = startDate;
		this.endDateTime = endDateTime;
		this.delay = 0;
		this.routeId = routeId;
		this.trainId = trainId;
		this.staffIds = staffIds;
	}

	public TimetableItem(LocalDateTime startDate, Long routeId) {
		this.startDateTime = startDate;
		this.endDateTime = null;
		this.delay = 0;
		this.routeId = routeId;
		this.trainId = null;
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
	
	public Long getTrainId() {
		return trainId;
	}
	
	public void setTrainId(Long trainId) {
		this.trainId = trainId;
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
	
	@Override
	public String toString() {
		return "Route " + this.routeId + ": " + this.startDateTime.toString() + " - " + this.endDateTime.toString();
	}
}
