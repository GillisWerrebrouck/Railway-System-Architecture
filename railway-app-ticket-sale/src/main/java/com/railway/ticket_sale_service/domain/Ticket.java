package com.railway.ticket_sale_service.domain;

import com.fasterxml.uuid.Generators;

import javax.persistence.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String startStation;
    private String endStation;
    private LocalDateTime validOn;
    private Long timetableId;
    private double price;
    private int amountOfSeats;
    private TicketType type;
    private UUID validationCode;
    private TicketStatus status;

    private UUID routeDetailsRequestId;
    private UUID reserveGroupSeatsRequestId;

    @SuppressWarnings("unused")
	private Ticket() {};

    public Ticket(String startStation, String endStation, LocalDateTime validOn, Long timetableId, double price, int amountOfSeats) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.validOn = validOn;
        this.timetableId = timetableId;
        this.price = price;
        this.amountOfSeats = amountOfSeats;
        validationCode = Generators.timeBasedGenerator().generate();
        if(amountOfSeats > 1)
            this.type = TicketType.GROUP;
        else
            this.type = TicketType.SINGLE;
    }

    public Ticket(LocalDateTime validOn, Long timetableId, int amountOfSeats){
        this(null, null, validOn, timetableId, 0, amountOfSeats);
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

    public UUID getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(UUID validationCode) {
        this.validationCode = validationCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getAmountOfSeats() {
		return amountOfSeats;
	}
    
    public void setAmountOfSeats(int amountOfSeats) {
		this.amountOfSeats = amountOfSeats;
	}
    
    public TicketType getType() {
		return type;
	}

    public UUID getRouteDetailsRequestId() {
        return routeDetailsRequestId;
    }

    public void setRouteDetailsRequestId(UUID routeDetailsRequestId) {
        this.routeDetailsRequestId = routeDetailsRequestId;
    }

    public UUID getReserveGroupSeatsRequestId() {
        return reserveGroupSeatsRequestId;
    }

    public void setReserveGroupSeatsRequestId(UUID reserveGroupSeatsRequestId) {
        this.reserveGroupSeatsRequestId = reserveGroupSeatsRequestId;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Long getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(Long timetableId) {
        this.timetableId = timetableId;
    }

    @Override
    public String toString() {
        String dateFormat = this.validOn.format(DateTimeFormatter.ofPattern("dd-LLLL-yyyy"));
        if(this.type == TicketType.GROUP)
            dateFormat += " " + this.validOn.format(DateTimeFormatter.ofPattern("HH:mm"));
        return MessageFormat.format("id: {0}, type: {1}, startStation: {2}, endStation: {3}" +
                        ", price: {4}, validOn: {5}, validationCode: {6}" , this.id, this.type, this.startStation,
                this.endStation, this.price, dateFormat, validationCode);
    }
}
