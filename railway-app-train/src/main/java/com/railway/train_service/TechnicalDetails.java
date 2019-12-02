package com.railway.train_service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class TechnicalDetails {

	private FuelType fuel;
	private LocalDate lastCheck;
	private Map <String,String> defects; 		// key: subject, value: description of defect
	
	
	private TechnicalDetails() {
	}


	public TechnicalDetails(FuelType fuel, LocalDate lastCheck, Map<String,String> defects) {
		this.fuel = fuel;
		this.lastCheck = lastCheck;
		this.defects = defects;
	}



	public FuelType getFuel() {
		return fuel;
	}


	public void setFuel(FuelType fuel) {
		this.fuel = fuel;
	}


	public LocalDate getLastCheck() {
		return lastCheck;
	}


	public void setLastCheck(LocalDate lastCheck) {
		this.lastCheck = lastCheck;
	}


	public Map<String,String> getDefects() {
		return defects;
	}


	public void setDefects(Map<String,String> defects) {
		this.defects = defects;
	}
	
	
	
	
}
