package com.railway.station_service.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@SuppressWarnings("serial")
public class ScheduleItemResponse implements Serializable {
	private UUID stationId;
	private String name;
	private Long platformId;
	private int platformNumber;
	private Long scheduleItemId;
	private LocalDateTime arrivalDateTime;
	private LocalDateTime departureDateTime;
	private int delay;
	
	public ScheduleItemResponse(UUID stationId, String name, Long platformId, int platformNumber, Long scheduleItemId, LocalDateTime arrivalDateTime, LocalDateTime departureDateTime, int delay) {
		this.stationId = stationId;
		this.name = name;
		this.platformId = platformId;
		this.platformNumber = platformNumber;
		this.scheduleItemId = scheduleItemId;
		this.arrivalDateTime = arrivalDateTime;
		this.departureDateTime = departureDateTime;
		this.delay = delay;
	}
	
	public UUID getStationId() {
		return stationId;
	}
	
	public String getName() {
		return name;
	}
	
	public Long getPlatformId() {
		return platformId;
	}
	
	public int getPlatformNumber() {
		return platformNumber;
	}
	
	public Long getScheduleItemId() {
		return scheduleItemId;
	}
	
	public LocalDateTime getArrivalDateTime() {
		return arrivalDateTime;
	}
	
	public LocalDateTime getDepartureDateTime() {
		return departureDateTime;
	}
	
	public int getDelay() {
		return delay;
	}
}
