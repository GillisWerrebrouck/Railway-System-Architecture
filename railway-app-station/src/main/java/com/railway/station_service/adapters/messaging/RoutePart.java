package com.railway.station_service.adapters.messaging;

public class RoutePart {
	private Long id;
    private StationOnRoute stationX;
    private StationOnRoute stationY;
	private Long distance;
	private double maxSpeed;
	private boolean active = true;
	
	public RoutePart(StationOnRoute stationX, StationOnRoute stationY, Long distance, double maxSpeed) {
		this.stationX = stationX;
		this.stationY = stationY;
		this.distance = distance;
		this.maxSpeed = maxSpeed;
	}
	
	public Long getId() {
		return id;
	}
	
	public StationOnRoute getStationX() {
		return stationX;
	}
	
	public StationOnRoute getStationY() {
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
