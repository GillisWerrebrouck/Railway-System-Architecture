package com.railway.station_service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Station {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "station_id")
	private int id;
	
	
	private String name;

	
	@Embedded
	private Address address;
	

	@OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
	private List<Platform> platforms;
	
	
	public Station() {
	}
	
	
	public Station(String name, Address address, ArrayList<Platform> platforms) {
		this.address = address;
		this.platforms = platforms;
		this.name = name;
	}
	

	public List<Platform> getPlatforms() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
