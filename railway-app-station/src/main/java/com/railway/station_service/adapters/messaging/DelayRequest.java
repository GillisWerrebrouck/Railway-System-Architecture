package com.railway.station_service.adapters.messaging;

import java.util.UUID;

import com.railway.station_service.domain.DelayState;


public class DelayRequest {

	private Long TimetableId;
	private Long routeId;
	private Long startStationId;
	private int delayInMinutes;
	private String reasonForDelay;
	private DelayState state;
	private UUID routeRequestId;
	
	



	public DelayRequest(Long timetableId, Long routeId, Long startStationId, int delayInMinutes, String reasonForDelay, DelayState state) {
		TimetableId = timetableId;
		this.routeId = routeId;
		this.startStationId = startStationId;
		this.delayInMinutes = delayInMinutes;
		this.reasonForDelay = reasonForDelay;
		this.state = state;
	}


	public Long getTimetableId() {
		return TimetableId;
	}


	public void setTimetableId(Long timetableId) {
		TimetableId = timetableId;
	}


	public Long getRouteId() {
		return routeId;
	}


	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}


	public Long getStartStationId() {
		return startStationId;
	}


	public void setStartStationId(Long startStationId) {
		this.startStationId = startStationId;
	}


	public int getDelayInMinutes() {
		return delayInMinutes;
	}


	public void setDelayInMinutes(int delayInMinutes) {
		this.delayInMinutes = delayInMinutes;
	}


	public String getReasonForDelay() {
		return reasonForDelay;
	}


	public void setReasonForDelay(String reasonForDelay) {
		this.reasonForDelay = reasonForDelay;
	}


	public DelayState getState() {
		return state;
	}


	public void setState(DelayState state) {
		this.state = state;
	}


	public UUID getRouteRequestId() {
		return routeRequestId;
	}


	public void setRouteRequestId(UUID routeRequestId) {
		this.routeRequestId = routeRequestId;
	}
	
	
}
