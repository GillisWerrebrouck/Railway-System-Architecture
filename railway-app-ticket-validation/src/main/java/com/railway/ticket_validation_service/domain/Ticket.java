package com.railway.ticket_validation_service.domain;

import com.railway.ticket_validation_service.adapters.messaging.TicketValidationEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.MessageFormat;
import java.time.LocalDate;
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
    @org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
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

    private static Logger logger = LoggerFactory.getLogger(TicketValidationEventHandler.class);

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

    public boolean isValid() { return isValid; }

    public void validate() {
        if(!isUsed && LocalDate.now().compareTo(LocalDate.from(validOn)) == 0) {
            isUsed = true;
            isValid = true;
        }else if(isValid == true && isUsed == true){
            isValid = false;
        }
    }

    @Override
    public String toString() {
        return MessageFormat.format("id: {0}, startStation: {1}, endStation: {2}" +
        							", amountOfSeats: {3}", this.id, this.startStation, this.endStation, this.amountOfSeats);
    }
}
