package com.railway.train_service.adapters.messaging;

import com.railway.train_service.domain.TrainStatus;

public class ChangeStatusRequest {
	private String trainId;
	private TrainStatus status;
	
	public ChangeStatusRequest(String trainId, TrainStatus status) {
		this.trainId = trainId;
		this.status = status;
	}

	public String getTrainId() {
		return trainId;
	}

	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}

	public TrainStatus getStatus() {
		return status;
	}

	public void setStatus(TrainStatus status) {
		this.status = status;
	}
	
	
}
