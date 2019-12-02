package com.railway.maintenance_service.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class Train implements Serializable {
	private Long trainId;
	private FuelType type;
	
	@SuppressWarnings("unused")
	private Train() {}
	
	public Train(Long trainId, FuelType type) {
		this.trainId = trainId;
		this.type = type;
	}

	public Long getTrainId() {
		return trainId;
	}
	
	public FuelType getType() {
		return type;
	}
	
	public void setType(FuelType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Train [id=" + trainId + ", type=" + type + "]";
	}
}
