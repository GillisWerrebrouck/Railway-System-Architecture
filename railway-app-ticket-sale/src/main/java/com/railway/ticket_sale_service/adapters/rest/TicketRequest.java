package com.railway.ticket_sale_service.adapters.rest;

import com.railway.ticket_sale_service.domain.TicketType;

import java.time.LocalDateTime;
import java.util.UUID;

public class TicketRequest {

    private String startStationId;
    private String endStationId;
    private LocalDateTime startDateTime;
    private Long timetableId;
    private int amountOfSeats;
    private TicketType ticketType;
    private UUID ticketCreationId;


    public TicketRequest(String startStationId, String endStationId, LocalDateTime startDateTime, Long timetableId, int amountOfSeats, TicketType ticketType) {
        this.startStationId = startStationId;
        this.endStationId = endStationId;
        this.startDateTime = startDateTime;
        this.timetableId = timetableId;
        this.amountOfSeats = amountOfSeats;
        this.ticketType = ticketType;
        this.ticketCreationId = UUID.randomUUID();
    }

    public String getStartStationId() {
        return startStationId;
    }

    public void setStartStationId(String startStationId) {
        this.startStationId = startStationId;
    }

    public String getEndStationId() {
        return endStationId;
    }

    public void setEndStationId(String endStationId) {
        this.endStationId = endStationId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public int getAmountOfSeats() {
        return amountOfSeats;
    }

    public void setAmountOfSeats(int amountOfSeats) {
        this.amountOfSeats = amountOfSeats;
    }

    public Long getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(Long timetableId) {
        this.timetableId = timetableId;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public UUID getTicketCreationId() {
        return ticketCreationId;
    }

}
