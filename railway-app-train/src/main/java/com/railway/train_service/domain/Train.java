package com.railway.train_service.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Train {
	@Id
	private String id;
	private TrainType type;
	private int totalCapacity;
	private int groupCapacity;
	private TrainStatus status;
	private TechnicalDetails technicaldetails;
	private List<ScheduleItem> scheduleItems;
	
	@SuppressWarnings("unused")
	private Train() {}
	
	public Train(TrainType type, int totalCapacity, int groupCapacity, TrainStatus status,
			TechnicalDetails technicaldetails, List<ScheduleItem> scheduleItems) {
		this.type = type;
		this.totalCapacity = totalCapacity;
		this.groupCapacity = groupCapacity;
		this.status = status;
		this.technicaldetails = technicaldetails;
		this.scheduleItems = scheduleItems;
	}
	
	public Train(TrainType type, int totalCapacity, int groupCapacity, TechnicalDetails technicaldetails) {
		this(type, totalCapacity, groupCapacity, TrainStatus.ACTIVE, technicaldetails, new ArrayList<>());
	}
	
	public List<ScheduleItem> getScheduleItems(){
		return scheduleItems;
	}
	
	public void setScheduleItems(List<ScheduleItem> scheduleItems){
		this.scheduleItems = scheduleItems;
	}
	
	public void addScheduleItem(ScheduleItem scheduleItem){
		this.scheduleItems.add(scheduleItem);
	}
	
	public boolean removeScheduleItem(ScheduleItem scheduleItem){
		return this.scheduleItems.remove(scheduleItem);
	}
	
	public String getId() {
		return id;
	}
	
	public TrainType getType() {
		return type;
	}
	
	public void setType(TrainType type) {
		this.type = type;
	}
		
	public int getTotalCapacity() {
		return totalCapacity;
	}
	
	public void setTotalCapacity(int totalCapacity) {
		this.totalCapacity = totalCapacity;
	}
	
	public int getGroupCapacity() {
		return groupCapacity;
	}
	
	public void setGroupCapacity(int groupCapacity) {
		this.groupCapacity = groupCapacity > this.totalCapacity ? this.totalCapacity : groupCapacity;
	}
	
	public TrainStatus getStatus() {
		return status;
	}
	
	public void setStatus(TrainStatus status) {
		this.status = status;
	}
	
	public TechnicalDetails getTechnicaldetails() {
		return technicaldetails;
	}
	
	public void setTechnicaldetails(TechnicalDetails technicaldetails) {
		this.technicaldetails = technicaldetails;
	}
	
	@Override
	public String toString() {
		return "id: " + this.id.toString() + ", type: " + this.type + ", total capacity: " + this.totalCapacity;
	}
}
