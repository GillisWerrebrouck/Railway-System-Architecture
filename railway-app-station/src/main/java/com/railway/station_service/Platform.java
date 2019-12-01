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
	private List<SheduleItem> reservableSlots = new ArrayList<SheduleItem>();
	
	
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
	
	public List<SheduleItem> getReservableSlots() {
		return reservableSlots;
	}
	
	public void setReservableSlots(ArrayList<SheduleItem> reservableSlots) {
		this.reservableSlots = reservableSlots;
	}
	
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	@Override
	public String toString() {
		return "Platform [id=" + id + ", platformNumber=" + platformNumber + ", reservableSlots=" + reservableSlots
				+ ", station=" + station + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + platformNumber;
		result = prime * result + ((reservableSlots == null) ? 0 : reservableSlots.hashCode());
		result = prime * result + ((station == null) ? 0 : station.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Platform other = (Platform) obj;
		if (id != other.id)
			return false;
		if (platformNumber != other.platformNumber)
			return false;
		if (reservableSlots == null) {
			if (other.reservableSlots != null)
				return false;
		} else if (!reservableSlots.equals(other.reservableSlots))
			return false;
		if (station == null) {
			if (other.station != null)
				return false;
		} else if (!station.equals(other.station))
			return false;
		return true;
	}
}
