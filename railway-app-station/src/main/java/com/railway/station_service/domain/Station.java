package com.railway.station_service.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "station")
public class Station {
	@Id
	@Type(type="org.hibernate.type.UUIDCharType")
	private UUID id;
	
	@Column(unique = true)
	private String name;
	
	@Embedded
	private Address address;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "station")
	private List<Platform> platforms = new ArrayList<Platform>();
	
	@SuppressWarnings("unused")
	private Station() {
		this(UUID.randomUUID());
	}
	
	public Station(UUID id) {
		this.id = id;
	}
	
	public Station(UUID id, String name, Address address) {
		this.id = id;
		this.name = name;
		this.address = address;
	}
	
	public Station(String name, Address address) {
		// the "id" field should be an auto-generated field but this is easier for development/testing purposes
		this(UUID.randomUUID(), name, address);
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
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
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
