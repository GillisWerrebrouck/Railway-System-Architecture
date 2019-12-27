package com.railway.ticket_sale_service.domain;

import java.util.List;

public interface BookTicketListener {

    public void onBookTicketResult(List<Ticket> tickets);

    public void onBookTicketError(List<Ticket> tickets, String errorMessage);
}
