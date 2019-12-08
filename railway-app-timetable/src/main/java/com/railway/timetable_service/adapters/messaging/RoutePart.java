package com.railway.timetable_service.adapters.messaging;

public class RoutePart {
    private Long id;
    private Station stationX;
    private Station stationY;
	private Long distance;
	private double maxSpeed;
	private boolean active = true;
	
	public RoutePart(Station stationX, Station stationY, Long distance, double maxSpeed) {
		this.stationX = stationX;
		this.stationY = stationY;
		this.distance = distance;
		this.maxSpeed = maxSpeed;
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
	
	public double getMaxSpeed() {
		return maxSpeed;
	}
	
	public boolean isActive() {
		return active;
	}
}
