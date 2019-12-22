package com.railway.station_service.adapters.messaging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class StationsRequest {
	private UUID requestId;
	private Long timetableId;
	private Collection<StationRequest> stationRequests;
	
	public StationsRequest(Long timetableId, Collection<StationRequest> stationRequests) {
		this.requestId = UUID.randomUUID();
		this.timetableId = timetableId;
		this.stationRequests = stationRequests;
	}
	
	public StationsRequest(Long timetableId) {
		this(timetableId, new ArrayList<StationRequest>());
	}
	
	public UUID getRequestId() {
		return requestId;
	}
	
	public Long getTimetableId() {
		return timetableId;
	}
	
	public Collection<StationRequest> getStationRequests() {
		return stationRequests;
	}
	
	public void addStationRequest(StationRequest stationRequest) {
		this.stationRequests.add(stationRequest);
	}
}
