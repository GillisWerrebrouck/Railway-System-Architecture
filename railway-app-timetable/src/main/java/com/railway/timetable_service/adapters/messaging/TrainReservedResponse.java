package com.railway.timetable_service.adapters.messaging;

import java.util.UUID;

public class TrainReservedResponse {
	private String trainId;
	private Long timetableId;
	private UUID requestId;
	
	public TrainReservedResponse(String trainId, Long timetableId, UUID requestId) {
		this.trainId = trainId;
		this.timetableId = timetableId;
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
}
