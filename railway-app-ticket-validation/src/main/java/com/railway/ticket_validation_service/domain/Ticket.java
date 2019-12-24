package com.railway.ticket_validation_service.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Ticket {
    @Id
    private Long id;
    private String startStation;
    private String endStation;
    private LocalDateTime validOn;
    private int amountOfSeats;
    private UUID validationCode;
    private boolean isUsed;
    private boolean isValid;

    @SuppressWarnings("unused")
	private Ticket() {};

    public Ticket(Long id, String startStation, String endStation, LocalDateTime validOn, int amountOfSeats, UUID validationCode) {
        this.id = id;
        this.startStation = startStation;
        this.endStation = endStation;
        this.validOn = validOn;
        this.amountOfSeats = amountOfSeats;
        this.validationCode = validationCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDateTime getValidOn() {
        return validOn;
    }

    public void setValidOn(LocalDateTime validOn) {
        this.validOn = validOn;
    }

    public int getAmountOfSeats() {
        return amountOfSeats;
    }

    public void setAmountOfSeats(int amountOfSeats) {
        this.amountOfSeats = amountOfSeats;
    }

    public UUID getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(UUID validationCode) {
        this.validationCode = validationCode;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
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
