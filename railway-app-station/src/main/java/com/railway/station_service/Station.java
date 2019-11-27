package com.railway.station_service;

import java.util.ArrayList;

public class Station {

	int id;
	Address address;
	ArrayList<Platform> platforms;
	ArrayList<InformationPanel> informationpanels;
	
	
	public Station(Address address, ArrayList<Platform> platforms,
			ArrayList<InformationPanel> informationpanels) {
		super();
		this.address = address;
		this.platforms = platforms;
		this.informationpanels = informationpanels;
	}

	public Station() {
		super();
	}

	public ArrayList<Platform> getPlatforms() {
		return platforms;
	}
	
	public void setPlatforms(ArrayList<Platform> platforms) {
		this.platforms = platforms;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public ArrayList<InformationPanel> getInformationpanels() {
		return informationpanels;
	}

	public void setInformationpanels(ArrayList<InformationPanel> informationpanels) {
		this.informationpanels = informationpanels;
	}

	
}
