package com.railway.station_service;

public class InformationPanel {


	int id;
	String currentinfo;
	
	
	public InformationPanel() {
		super();
	}
	
	public InformationPanel(String currentinfo) {
		super();
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
