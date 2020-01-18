package com.railway.station_service.adapters.messaging;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@SuppressWarnings("serial")
@RedisHash("DelayRequest")
public class DelayRequest implements Serializable {
	private Long timetableId;
	private Long routeId;
	@org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
	private UUID startStationId;
	private int delayInMinutes;
	private String reasonForDelay;
	// routeRequestId is the property that will be used to identify a request in the key-value store
	@Id
	private String routeRequestId;
	
	public DelayRequest(Long timetableId, Long routeId, UUID startStationId, int delayInMinutes, String reasonForDelay) {
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

	public String getRouteRequestId() {
		return routeRequestId;
	}

	public void setRouteRequestId(UUID routeRequestId) {
		this.routeRequestId = routeRequestId.toString();
	}

	public void setRouteRequestId(String routeRequestId) {
		this.routeRequestId = routeRequestId;
	}
}
