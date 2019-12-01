package com.railway.maintenance_service.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
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
	private String comment;
	@Embedded
	private Train train;
	
	@SuppressWarnings("unused")
	private ScheduleItem() {}

	public ScheduleItem(LocalDateTime startDate, LocalDateTime endDate, Status status, String comment) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.comment = comment;
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

	@Override
	public String toString() {
		return "ScheduleItem [id=" + id + ", startDate=" + startDate + ", status=" + status + ", comment=" + comment + "]";
	}
}
