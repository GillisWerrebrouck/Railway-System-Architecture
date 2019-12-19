package com.railway.maintenance_service.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

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
	private Collection<String> staffIds;
	private UUID requestId;
	private String staffReservationMessage;
	private Status status;
	private String maintenanceMessage;
	private String trainId;
	private MaintenanceType maintenanceType;
	
	@SuppressWarnings("unused")
	private ScheduleItem() {}

	public ScheduleItem(String trainId, LocalDateTime startDate, LocalDateTime endDate, Status status, String comment, MaintenanceType maintenanceType) {
		this.trainId = trainId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.maintenanceMessage = comment;
		this.staffIds = new ArrayList<>();
		this.maintenanceType= maintenanceType;
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
	
	public Collection<String> getStaffIds() {
		return staffIds;
	}
	
	public void setStaffIds(Collection<String> staffIds) {
		this.staffIds = staffIds;
	}
	
	public UUID getRequestId() {
		return requestId;
	}
	
	public void setRequestId(UUID requestId) {
		this.requestId = requestId;
	}
	
	public String getStaffReservationMessage() {
		return staffReservationMessage;
	}
	
	public void setStaffReservationMessage(String staffReservationMessage) {
		this.staffReservationMessage = staffReservationMessage;
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
	

	public MaintenanceType getMaintenanceType() {
		return maintenanceType;
	}

	public void setMaintenanceType(MaintenanceType maintenanceType) {
		this.maintenanceType = maintenanceType;
	}

	@Override
	public String toString() {
		return "id: " + id + ", startDate: " + startDate + ", status: " + status + ", maintenance message: " + maintenanceMessage;
	}
}
