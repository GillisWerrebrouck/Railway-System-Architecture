package com.railway.train_service.adapters.messaging;

public class TrainOutOfServiceRequest {
	private String trainId;
	private Long timeTableId;
	
	public TrainOutOfServiceRequest(String trainId, Long timeTableId) {
		this.trainId = trainId;
		this.timeTableId = timeTableId;
	}

	public String getTrainId() {
		return trainId;
	}

	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}

	public Long getTimeTableId() {
		return timeTableId;
	}

	public void setTimeTableId(Long timeTableId) {
		this.timeTableId = timeTableId;
	}
}
