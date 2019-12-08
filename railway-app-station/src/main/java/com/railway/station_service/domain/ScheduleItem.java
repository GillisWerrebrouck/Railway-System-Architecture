package com.railway.station_service.domain;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"platform"})
public class ScheduleItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long timetableId;
	private LocalDateTime arrivalDateTime;
	private LocalDateTime departureDateTime;
	private int delayInMinutes;
	
	@ManyToOne
	private Platform platform;
	
	@SuppressWarnings("unused")
	private ScheduleItem() {}
	
	public ScheduleItem(Long timetableId, LocalDateTime arrivalDateTime, LocalDateTime departureDateTime, int delayInMinutes) {
		this.timetableId = timetableId;
		this.arrivalDateTime = arrivalDateTime;
		this.departureDateTime = departureDateTime;
		this.delayInMinutes = delayInMinutes;
	}
	
	public ScheduleItem(Long timetableId, LocalDateTime arrivalDateTime, LocalDateTime departureDateTime) {
		this(timetableId, arrivalDateTime, departureDateTime, 0);
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Long getTimetableId() {
		return timetableId;
	}
	
	public void setTimetableId(Long timetableId) {
		this.timetableId = timetableId;
	}

	public LocalDateTime getArrivalDateTime() {
		return arrivalDateTime;
	}

	public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}

	public LocalDateTime getDepartureDateTime() {
		return departureDateTime;
	}

	public void setDepartureDateTime(LocalDateTime departureDateTime) {
		this.departureDateTime = departureDateTime;
	}

	public int getDelayInMinutes() {
		return delayInMinutes;
	}

	public void setDelayInMinutes(int delayInMinutes) {
		this.delayInMinutes = delayInMinutes;
	}

	@Override
	public String toString() {
		return "SheduleItem [id=" + id + ", timetableId=" + timetableId + ", arrivalDateTime=" + arrivalDateTime
				+ ", departureDateTime=" + departureDateTime + ", delayInMinutes=" + delayInMinutes + ", platform="
				+ platform + "]";
	}
}
