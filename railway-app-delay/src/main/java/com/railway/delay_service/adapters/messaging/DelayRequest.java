package com.railway.delay_service.adapters.messaging;

import java.util.UUID;

public class DelayRequest {

	private UUID TimetableId;
	private UUID routeId;
	private UUID startStationId;
	private int delayInMinutes;
	private String reasonForDelay; 
	
	
	public DelayRequest(UUID timetableId, UUID routeId, UUID startStationId, int delayInMinutes,String reasonForDelay) {
		TimetableId = timetableId;
		this.routeId = routeId;
		this.startStationId = startStationId;
		this.delayInMinutes = delayInMinutes;
		this.reasonForDelay = reasonForDelay;
	}


	public UUID getTimetableId() {
		return TimetableId;
	}


	public void setTimetableId(UUID timetableId) {
		TimetableId = timetableId;
	}


	public UUID getRouteId() {
		return routeId;
	}


	public void setRouteId(UUID routeId) {
		this.routeId = routeId;
	}


	public UUID getStartStationId() {
		return startStationId;
	}


	public void setStartStationId(UUID startStationId) {
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
	
	
}
