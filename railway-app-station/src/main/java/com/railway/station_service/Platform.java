package com.railway.station_service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties("station")
public class Platform implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	
	private int platformNumber;
	
	
	@OneToMany(mappedBy = "platform")
	private List<InformationPanel> informationpanels = new ArrayList<InformationPanel>();
	
	
    @ManyToOne
	private Station station;
    
	
    private Platform() {
	}
	

    public Platform(int platformNumber) {
		this.platformNumber = platformNumber;
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
	
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
	
}
