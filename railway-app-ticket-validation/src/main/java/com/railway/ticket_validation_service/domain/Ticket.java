package com.railway.ticket_validation_service.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
public class Ticket {

    @Id
    private UUID id;
    private String startStation;
    private String endStation;
    private LocalDate validOn;
    private int amount;
    private boolean used;

    private Ticket() {};

    public Ticket(UUID id, String startStation, String endStation, LocalDate validOn, int amount) {
        this.id = id;
        this.startStation = startStation;
        this.endStation = endStation;
        this.validOn = validOn;
        this.amount = amount;
        used = false;
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

    public LocalDate getValidOn() {
        return validOn;
    }

    public void setValidOn(LocalDate validOn) {
        this.validOn = validOn;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return MessageFormat.format("id: {0}\t startStation: {1}\t endStation: {2}" +
                        "\t validOn: {3}\t amount: {4}" , this.id, this.startStation, this.endStation,
                this.validOn.format(DateTimeFormatter.ofPattern("dd-LLLL-yyyy")), this.amount);
    }
}
