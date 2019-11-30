package com.railway.maintenance_service.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ScheduleMaintenance {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDateTime maintenanceDate;
	//private List<Staff> staff;
	private Status status;
	private String comment;
	private String train;
	
	@SuppressWarnings("unused")
	private ScheduleMaintenance() {
		this.maintenanceDate = null;
		this.status = null;
		this.comment = null;
	}

	public ScheduleMaintenance(LocalDateTime maintenanceDate, Status status, String comment) {
		this.maintenanceDate = maintenanceDate;
		this.status = status;
		this.comment = comment;
	}
	
	public LocalDateTime getMaintenanceDate() {
		return maintenanceDate;
	}

	public void setMaintenanceDate(LocalDateTime maintenanceDate) {
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

	public String getTrain() {
		return train;
	}

	public void setTrain(String train) {
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
