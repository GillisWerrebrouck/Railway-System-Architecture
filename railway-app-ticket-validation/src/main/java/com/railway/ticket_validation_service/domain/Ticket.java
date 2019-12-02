package com.railway.ticket_validation_service.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Ticket {
    @Id
    private UUID id;
    private String startStation;
    private String endStation;
    private LocalDateTime validOn;
    private int amountOfSeats;
    private boolean isUsed;
    private boolean isValid;

    @SuppressWarnings("unused")
	private Ticket() {};

    public Ticket(UUID id, String startStation, String endStation, LocalDateTime validOn, int amountOfSeats) {
        this.id = id;
        this.startStation = startStation;
        this.endStation = endStation;
        this.validOn = validOn;
        this.amountOfSeats = amountOfSeats;
        isUsed = false;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public LocalDateTime getValidOnDate() {
        return validOn;
    }

    public void setValidOnDate(LocalDateTime validOn) {
        this.validOn = validOn;
    }

    public int getAmountOfSeats() {
        return amountOfSeats;
    }

    public void setAmountOfSeats(int amountOfSeats) {
        this.amountOfSeats = amountOfSeats;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        this.isUsed = used;
    }

    // check if the due date of the ticket hasn't passed and that the ticket hasn't been used before
    public boolean isValid() {	
        return !isUsed && validOn.compareTo(LocalDateTime.now()) <= 0;
    }

    public void validate(){	
        if(isValid) {
            isUsed = true;
        }
    }

    @Override
    public String toString() {
        return MessageFormat.format("id: {0}, startStation: {1}, endStation: {2}" +
        							", amountOfSeats: {3}", this.id, this.startStation, this.endStation, this.amountOfSeats);
    }
}
