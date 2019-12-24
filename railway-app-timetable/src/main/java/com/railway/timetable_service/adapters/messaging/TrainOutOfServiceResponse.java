package com.railway.timetable_service.adapters.messaging;

public class TrainOutOfServiceResponse {
	private String trainId;
	private Long timeTableId;
	
	public TrainOutOfServiceResponse(String trainId, Long timeTableId) {
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
