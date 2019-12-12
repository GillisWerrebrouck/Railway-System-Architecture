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
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	@ElementCollection
	private List<Staff> staff;
	private Status status;
	private String maintenanceMessage;
	private String trainId;
	
	@SuppressWarnings("unused")
	private ScheduleItem() {}

	public ScheduleItem(String trainId, LocalDateTime startDate, LocalDateTime endDate, Status status, String comment) {
		this.trainId = trainId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.maintenanceMessage = comment;
		this.staff = new ArrayList<Staff>();
	}

	public Long getId() {
		return id;
	}
	
	public LocalDateTime getStartDate() {
		return startDate;
	}
	
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	
	public LocalDateTime getEndDate() {
		return endDate;
	}
	
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public List<Staff> getStaff() {
		return staff;
	}

	public void setStaff(List<Staff> staff) {
		this.staff = staff;
	}

	public void addStaff(Staff staff) {
		this.staff.add(staff);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMaintenanceMessage() {
		return maintenanceMessage;
	}
	
	public void setMaintenanceMessage(String maintenanceMessage) {
		this.maintenanceMessage = maintenanceMessage;
	}
	
	public String getTrainId() {
		return trainId;
	}
	
	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}

	@Override
	public String toString() {
		return "id: " + id + ", startDate: " + startDate + ", status: " + status + ", comment: " + maintenanceMessage;
	}
}
