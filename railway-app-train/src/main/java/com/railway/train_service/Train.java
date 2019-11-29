package com.railway.train_service;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

enum Type {IC, IR, P}
enum Status {ACTIVE, NONACTIVE}

@Document
public class Train {
	
	@Id
	private String id;
	private Type type;
	private double avgSpeed;
	private int totalCapacity;
	private int groupCapacity;
	private Status status;
	private TechnicalDetails technicaldetails;
	
	
	private Train() {
	}


	public Train(Type type, double avgSpeed, int totalCapacity, int groupCapacity, Status status,
			TechnicalDetails technicaldetails) {
		this.type = type;
		this.avgSpeed = avgSpeed;
		this.totalCapacity = totalCapacity;
		this.groupCapacity = groupCapacity;
		this.status = status;
		this.technicaldetails = technicaldetails;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Type getType() {
		return type;
	}


	public void setType(Type type) {
		this.type = type;
	}


	public double getAvgSpeed() {
		return avgSpeed;
	}


	public void setAvgSpeed(double avgSpeed) {
		this.avgSpeed = avgSpeed;
	}


	public int getTotalCapacity() {
		return totalCapacity;
	}


	public void setTotalCapacity(int totalCapacity) {
		this.totalCapacity = totalCapacity;
	}


	public int getGroupCapacity() {
		return groupCapacity;
	}


	public void setGroupCapacity(int groupCapacity) {
		this.groupCapacity = groupCapacity;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public TechnicalDetails getTechnicaldetails() {
		return technicaldetails;
	}


	public void setTechnicaldetails(TechnicalDetails technicaldetails) {
		this.technicaldetails = technicaldetails;
	}
	
	
	

	
	
}
