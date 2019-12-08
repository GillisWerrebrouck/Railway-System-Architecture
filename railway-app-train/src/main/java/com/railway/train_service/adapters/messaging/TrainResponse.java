package com.railway.train_service.adapters.messaging;

import java.util.UUID;

public class TrainResponse {
	private String trainId;
	private Long timetableId;
	private UUID requestId;
	
	public TrainResponse(String trainId, Long timetableId, UUID requestId) {
		this.trainId = trainId;
		this.timetableId = timetableId;
	}
	
	public String getTrainId() {
		return trainId;
	}
	
	public Long getTimetableId() {
		return timetableId;
	}
	
	public UUID getRequestId() {
		return requestId;
	}
}
