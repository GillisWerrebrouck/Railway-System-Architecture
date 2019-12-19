package com.railway.api_gateway.adapters.messaging;

public class EmergencyRequest {

	private Long trainId;
	private Long routeId;
	private String nearestStation;
	private String message;
	
	public EmergencyRequest(Long trainId, Long routeId, String nearestStation, String message) {
		this.trainId = trainId;
		this.routeId = routeId;
		this.nearestStation = nearestStation;
		this.message = message;
	}

	public Long getTrainId() {
		return trainId;
	}

	public void setTrainId(Long trainId) {
		this.trainId = trainId;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public String getNearestStation() {
		return nearestStation;
	}

	public void setNearestStation(String nearestStation) {
		this.nearestStation = nearestStation;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
