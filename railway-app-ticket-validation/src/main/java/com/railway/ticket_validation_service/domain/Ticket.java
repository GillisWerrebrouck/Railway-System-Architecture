package com.railway.ticket_validation_service.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 *
 */
@Entity
public class Ticket {

    @Id
    private UUID id;
    private String startStation;
    private String endStation;
    private LocalDate validOnDate;
    private int amountOfSeats;
    private boolean isUsed;
    private boolean isValid;

    private Ticket() {};

    public Ticket(UUID id, String startStation, String endStation, LocalDate validOnDate, int amountOfSeats) {
        this.id = id;
        this.startStation = startStation;
        this.endStation = endStation;
        this.validOnDate = validOnDate;
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

    public LocalDate getValidOnDate() {
        return validOnDate;
    }

    public void setValidOnDate(LocalDate validOnDate) {
        this.validOnDate = validOnDate;
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

    public boolean isValid() {
        return isValid;
    }


    /**
     * when ticket is not used and is still not validated and the validOnDate is the same as the scanDate (=today)
     * it will set isValid and isUsed on true because the ticket is valid and it is used once scanned
     * if the ticket isUsed, and it is scanned again it will not be valid anymore.
     */
    public void validate(){
        if(!isUsed && !isValid && validOnDate.compareTo(LocalDate.now()) == 0){
            isValid = true;
            isUsed = true;
        }else if(isUsed){
            isValid = false;
        }
    }

    @Override
    public String toString() {
        return MessageFormat.format("id: {0}\t startStation: {1}\t endStation: {2}" +
                        "\t amountOfSeats: {4}\t", this.id, this.startStation, this.endStation,
                this.amountOfSeats);
    }

}
