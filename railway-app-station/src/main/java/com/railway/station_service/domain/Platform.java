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
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "platform")
	private List<ScheduleItem> reservedSlots = new ArrayList<ScheduleItem>();
	
    @ManyToOne
	private Station station;
    
    @SuppressWarnings("unused")
	private Platform() {}
    
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
	
	public List<ScheduleItem> getReservedSlots() {
		return reservedSlots;
	}
	
	public void setReservedSlots(ArrayList<ScheduleItem> reservableSlots) {
		this.reservedSlots = reservableSlots;
	}
	
	public void addReservedSlot(ScheduleItem slot) {
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
		return "id=" + id + ", platformNumber=" + platformNumber + ", #reservableSlots=" + reservedSlots.size()
				+ ", station=" + station.toString();
	}
}
