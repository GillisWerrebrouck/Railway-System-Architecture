package com.railway.station_service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


@Entity
public class Platform {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "platform_id")
	int id;
	
	
	int platformNumber;
	
	
	@OneToMany(mappedBy = "platform", cascade = CascadeType.ALL)
	private List<InformationPanel> informationpanels;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
	private Station station;
    
	
    private Platform() {
	}
	

    public Platform(int platformNumber, Station station) {
		this.platformNumber = platformNumber;
		this.station = station;
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
