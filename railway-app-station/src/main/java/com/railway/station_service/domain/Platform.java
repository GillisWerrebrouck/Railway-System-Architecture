package com.railway.station_service.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties("station")
public class Platform{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private int platformNumber;
	
	@OneToMany(mappedBy = "platform")
	private List<SheduleItem> reservedSlots = new ArrayList<SheduleItem>();
	
    @ManyToOne
	private Station station;
    
    @SuppressWarnings("unused")
	private Platform() {
	}
    
    public Platform(int platformNumber) {
		this.platformNumber = platformNumber;
	}
    
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getPlatformNumber() {
		return platformNumber;
	}
	
	public void setPlatformNumber(int platformNumber) {
		this.platformNumber = platformNumber;
	}
	
	public List<SheduleItem> getReservedSlots() {
		return reservedSlots;
	}
	
	public void setReservedSlots(ArrayList<SheduleItem> reservableSlots) {
		this.reservedSlots = reservableSlots;
	}
	
	public void addReservedSlot(SheduleItem slot) {
		this.reservedSlots.add(slot);
	}
	
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	@Override
	public String toString() {
		return "Platform [id=" + id + ", platformNumber=" + platformNumber + ", #reservableSlots=" + reservedSlots.size()
				+ ", station=" + station + "]";
	}
}
