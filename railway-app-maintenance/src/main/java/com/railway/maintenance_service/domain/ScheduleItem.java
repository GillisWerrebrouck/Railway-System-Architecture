package com.railway.maintenance_service.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ScheduleItem {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDateTime maintenanceDate;
	
	@ElementCollection(targetClass=Staff.class)
	private List<Staff> staff;
	private Status status;
	private String comment;
	private Train train;
	
	@SuppressWarnings("unused")
	private ScheduleItem() {
		
	}

	public ScheduleItem(LocalDateTime maintenanceDate, Status status, String comment) {
		this.maintenanceDate = maintenanceDate;
		this.status = status;
		this.comment = comment;
		this.staff= new ArrayList<>();
	}
	
	public LocalDateTime getMaintenanceDate() {
		return maintenanceDate;
	}

	public void setMaintenanceDate(LocalDateTime maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}

	public List<Staff> getStaff() {
		return staff;
	}

	public void setStaff(List<Staff> staff) {
		this.staff = staff;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ScheduleItem [id=" + id + ", maintenanceDate="
				+ maintenanceDate + ", status=" + status + ", comment=" + comment + "]";
	}
	
}
