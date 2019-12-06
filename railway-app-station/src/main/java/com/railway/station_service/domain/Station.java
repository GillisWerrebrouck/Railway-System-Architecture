package com.railway.station_service.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "station")
public class Station {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Embedded
	private Address address;
	
	@OneToMany(mappedBy = "station")
	private List<Platform> platforms = new ArrayList<Platform>();
	
	@SuppressWarnings("unused")
	private Station() {}
	
	public Station(String name, Address address) {
		this.address = address;
		this.name = name;
	}
	
	public List<Platform> getPlatforms() {
		return platforms;
	}
	
	public void setPlatforms(List<Platform> platforms) {
		this.platforms = platforms;
	}
	
	public void addPlatform(Platform platform) {
		this.platforms.add(platform);
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
		return "id:" + id + ", name:" + name + ", address: " + address.toString() + ", #platforms:" + platforms.size();
	}
}
