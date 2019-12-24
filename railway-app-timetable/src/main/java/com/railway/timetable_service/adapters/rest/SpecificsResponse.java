package com.railway.timetable_service.adapters.rest;

import java.time.LocalDate;

public class SpecificsResponse {
	private String trainId;
	private TrainType trainType;
	private int totalCapacity;
	private FuelType fuelType;
	private LocalDate lastCheck;
	
	public SpecificsResponse(String trainId, TrainType trainType, int totalCapacity, FuelType fuelType, LocalDate lastCheck) {
		this.trainId = trainId;
		this.trainType = trainType;
		this.totalCapacity = totalCapacity;
		this.fuelType = fuelType;
		this.lastCheck = lastCheck;
	}
	
	public String getTrainId() {
		return trainId;
	}
	
	public TrainType getTrainType() {
		return trainType;
	}
	
	public int getTotalCapacity() {
		return totalCapacity;
	}
	
	public FuelType getFuelType() {
		return fuelType;
	}
	
	public LocalDate getLastCheck() {
		return lastCheck;
	}
}
