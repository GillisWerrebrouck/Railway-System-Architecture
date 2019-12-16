package com.railway.train_service.adapters.messaging;

import java.util.UUID;

public class TrainResponse {
	private String trainId;
	private Long timetableId;
	private int groupCapacity;
	private UUID requestId;
	
	public TrainResponse(String trainId, Long timetableId, int groupCapacity, UUID requestId) {
		this.trainId = trainId;
		this.timetableId = timetableId;
		this.groupCapacity = groupCapacity;
		this.requestId = requestId;
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

	public int getGroupCapacity() {
		return groupCapacity;
	}

}
