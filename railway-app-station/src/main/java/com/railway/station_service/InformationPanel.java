package com.railway.station_service;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;

@Entity
public class InformationPanel {

	@Id
	@GeneratedValue
	private int id;
	
	private String currentinfo;

	@ManyToOne
	private Platform platform;
	
	
	public InformationPanel() {
	}
	

	
	public InformationPanel(String currentinfo) {
		this.currentinfo = currentinfo;
	}



	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCurrentinfo() {
		return currentinfo;
	}
	
	public void setCurrentinfo(String currentinfo) {
		this.currentinfo = currentinfo;
	}

	
	
}
