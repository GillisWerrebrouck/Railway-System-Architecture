package com.railway.train_service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

enum Fuel {ELECTRICITY, DIESEL}

@Document
public class TechnicalDetails {

	@Id
	private String id;
	private Fuel fuel;
	private LocalDate lastCheck;
	private Map <String,String> defects; 						/// onderwerp, beschrijvingen van de defecten
	
	
	private TechnicalDetails() {
	}


	public TechnicalDetails(Fuel fuel, LocalDate lastCheck, Map<String,String> defects) {
		this.fuel = fuel;
		this.lastCheck = lastCheck;
		this.defects = defects;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Fuel getFuel() {
		return fuel;
	}


	public void setFuel(Fuel fuel) {
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
