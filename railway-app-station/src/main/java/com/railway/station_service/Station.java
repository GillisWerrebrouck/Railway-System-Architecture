package com.railway.station_service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "station")
public class Station implements Serializable {

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private int id;
	
	
	private String name;

	
	@Embedded
	private Address address;
	

	@OneToMany(mappedBy = "station")
	private List<Platform> platforms = new ArrayList<Platform>();
	
	
	private Station() {
	}
	
	
	public Station(String name, Address address) {
		this.address = address;
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
