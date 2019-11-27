package com.railway.station_service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Platform {

	@Id
	@GeneratedValue
	int id;
	
	int platformNumber;
	
	@OneToMany(mappedBy = "platform", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<InformationPanel> informationpanels;
	
    @ManyToOne
	private Station station;
	
	
	public Platform() {
	}
	
	
	public Platform(int platformNumber, List<InformationPanel> informationpanels) {
		this.platformNumber = platformNumber;
		this.informationpanels = informationpanels;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPlatformNumber() {
		return platformNumber;
	}
	public void setPlatformNumber(int platformNumber) {
		this.platformNumber = platformNumber;
	}
	public List<InformationPanel> getInformationpanels() {
		return informationpanels;
	}
	public void setInformationpanels(ArrayList<InformationPanel> informationpanels) {
		this.informationpanels = informationpanels;
	}
	
	
	
}
