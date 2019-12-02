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
    private double price;
    private int amountOfSeats;
    private TicketType type;
    private UUID validationCode;

    @SuppressWarnings("unused")
	private Ticket() {};

    public Ticket(String startStation, String endStation, LocalDateTime validOn, double price, int amountOfSeats) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.validOn = validOn;
        this.price = price;
        this.amountOfSeats = amountOfSeats;
        this.type = TicketType.GROUP;
        validationCode = Generators.timeBasedGenerator().generate();
    }

    public Ticket(String startStation, String endStation, LocalDateTime validOn, double price) {
        this(startStation, endStation, validOn, price, 1);
        this.type = TicketType.SINGLE;
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
    
    public void setType(TicketType type) {
		this.type = type;
	}

    @Override
    public String toString() {
        String dateFormat = this.validOn.format(DateTimeFormatter.ofPattern("dd-LLLL-yyyy"));
        if(this.type == TicketType.GROUP)
            dateFormat += " " + this.validOn.format(DateTimeFormatter.ofPattern("HH:mm"));
        return MessageFormat.format("id: {0}\t type: {1}\t startStation: {2}\t endStation: {3}" +
                        "\t price: {4}\t validOn: {5}\t validationCode: {6}" , this.id, this.type, this.startStation,
                this.endStation, this.price, dateFormat, validationCode);
    }
}
