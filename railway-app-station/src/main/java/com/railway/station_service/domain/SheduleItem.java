package com.railway.station_service.domain;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"platform"})
public class SheduleItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private int trainId;
	private LocalDateTime arrivalDateTime;
	private LocalDateTime departureDateTime;
	private int delayInMinutes;
	
	@ManyToOne
	private Platform platform;
	
	@SuppressWarnings("unused")
	private SheduleItem() {}
	
	public SheduleItem(int trainId, LocalDateTime arrivalDateTime, LocalDateTime departureDateTime, int delayInMinutes) {
		this.trainId = trainId;
		this.arrivalDateTime = arrivalDateTime;
		this.departureDateTime = departureDateTime;
		this.delayInMinutes = delayInMinutes;
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

	public int getTrainId() {
		return trainId;
	}

	public void setTrainId(int trainId) {
		this.trainId = trainId;
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
		return "SheduleItem [id=" + id + ", trainId=" + trainId + ", arrivalDateTime=" + arrivalDateTime
				+ ", departureDateTime=" + departureDateTime + ", delayInMinutes=" + delayInMinutes + ", platform="
				+ platform + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrivalDateTime == null) ? 0 : arrivalDateTime.hashCode());
		result = prime * result + delayInMinutes;
		result = prime * result + ((departureDateTime == null) ? 0 : departureDateTime.hashCode());
		result = (int) (prime * result + id);
		result = prime * result + ((platform == null) ? 0 : platform.hashCode());
		result = prime * result + trainId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SheduleItem other = (SheduleItem) obj;
		if (arrivalDateTime == null) {
			if (other.arrivalDateTime != null)
				return false;
		} else if (!arrivalDateTime.equals(other.arrivalDateTime))
			return false;
		if (delayInMinutes != other.delayInMinutes)
			return false;
		if (departureDateTime == null) {
			if (other.departureDateTime != null)
				return false;
		} else if (!departureDateTime.equals(other.departureDateTime))
			return false;
		if (id != other.id)
			return false;
		if (platform == null) {
			if (other.platform != null)
				return false;
		} else if (!platform.equals(other.platform))
			return false;
		if (trainId != other.trainId)
			return false;
		return true;
	}
}
