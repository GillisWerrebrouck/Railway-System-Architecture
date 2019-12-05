package com.railway.timetable_service.adapters.messaging;

public class RoutePart {
    private Long id;
    private Station stationX;
    private Station stationY;
	private Long distance;
	private boolean active = true;
	
	public RoutePart(Station stationX, Station stationY, Long distance) {
		this.stationX = stationX;
		this.stationY = stationY;
		this.distance = distance;
	}
	
	public Long getId() {
		return id;
	}
	
	public Station getStationX() {
		return stationX;
	}
	
	public Station getStationY() {
		return stationY;
	}
	
	public Long getDistance() {
		return distance;
	}
	
	public boolean isActive() {
		return active;
	}
}
