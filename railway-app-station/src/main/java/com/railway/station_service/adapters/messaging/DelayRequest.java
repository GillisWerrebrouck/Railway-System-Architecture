package com.railway.station_service.adapters.messaging;

import java.util.UUID;

public class DelayRequest { 

	private Long timetableId;
	private Long routeId;
	private Long startStationId;
	private int delayInMinutes;
	private String reasonForDelay;
	private UUID routeRequestId;

	
	public DelayRequest(Long timetableId, Long routeId, Long startStationId, int delayInMinutes, String reasonForDelay) {
		this.timetableId = timetableId;
		this.routeId = routeId;
		this.startStationId = startStationId;
		this.delayInMinutes = delayInMinutes;
		this.reasonForDelay = reasonForDelay;
	}


	public Long getTimetableId() {
		return timetableId;
	}


	public void setTimetableId(Long timetableId) {
		this.timetableId = timetableId;
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


	public UUID getRouteRequestId() {
		return routeRequestId;
	}


	public void setRouteRequestId(UUID routeRequestId) {
		this.routeRequestId = routeRequestId;
	}
	
}
