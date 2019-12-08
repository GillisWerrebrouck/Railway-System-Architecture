package com.railway.timetable_service.adapters.messaging;

import java.util.UUID;

public class TrainReservedResponse {
	private Long trainId;
	private Long timetableId;
	private UUID requestId;
	
	public TrainReservedResponse(Long trainId, Long timetableId, UUID requestId) {
		this.trainId = trainId;
		this.timetableId = timetableId;
		this.requestId = requestId;
	}
	
	public Long getTrainId() {
		return trainId;
	}
	
	public Long getTimetableId() {
		return timetableId;
	}
	
	public UUID getRequestId() {
		return requestId;
	}
}
