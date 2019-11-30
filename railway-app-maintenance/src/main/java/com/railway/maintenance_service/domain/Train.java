package com.railway.maintenance_service.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Train implements Serializable {
	private Long trainId;
	private TrainType type;
	
	@SuppressWarnings("unused")
	private Train() {}
	
	public Train(Long trainId, TrainType type) {
		this.trainId = trainId;
		this.type = type;
	}

	public Long getTrainId() {
		return trainId;
	}
	
	public TrainType getType() {
		return type;
	}
	
	public void setType(TrainType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Train [id=" + trainId + ", type=" + type + "]";
	}
}
