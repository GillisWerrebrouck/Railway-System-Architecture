package com.railway.station_service.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "station")
public class Station {
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@Embedded
	private Address address;
	
	@OneToMany(mappedBy = "station")
	private List<Platform> platforms = new ArrayList<Platform>();
	
	@SuppressWarnings("unused")
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
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
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

	@Override
	public String toString() {
		return "Station [id=" + id + ", name=" + name + ", address=" + address + ", #platforms=" + platforms.size() + "]";
	}
}
