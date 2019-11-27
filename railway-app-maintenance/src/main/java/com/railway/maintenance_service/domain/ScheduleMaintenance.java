package com.railway.maintenance_service.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ScheduleMaintenance {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String maintenanceId;
	private LocalDate maintenanceDate;
	//private List<Staff> staff;
	private Status status;
	private String comment;
	//private Train train;
	
	@SuppressWarnings("unused")
	private ScheduleMaintenance() {
		this.maintenanceId = null;
		this.maintenanceDate = null;
		this.status = null;
		this.comment = null;
	}

	public ScheduleMaintenance(String maintenanceId, LocalDate maintenanceDate, Status status, String comment) {
		this.maintenanceId = maintenanceId;
		this.maintenanceDate = maintenanceDate;
		this.status = status;
		this.comment = comment;
	}

	public String getMaintenanceId() {
		return maintenanceId;
	}

	public void setMaintenanceId(String maintenanceId) {
		this.maintenanceId = maintenanceId;
	}

	public LocalDate getMaintenanceDate() {
		return maintenanceDate;
	}

	public void setMaintenanceDate(LocalDate maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}
/*
	public List<Staff> getStaff() {
		return staff;
	}

	public void setStaff(List<Staff> staff) {
		this.staff = staff;
	}
*/
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
/*
	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}
*/
	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ScheduleMaintenance [id=" + id + ", maintenanceId=" + maintenanceId + ", maintenanceDate="
				+ maintenanceDate + ", status=" + status + ", comment=" + comment + "]";
	}
	
}
