package com.railway.ticket_sale_service.adapters.rest;

import com.railway.ticket_sale_service.domain.BookTicketListener;
import com.railway.ticket_sale_service.domain.Ticket;
import com.railway.ticket_sale_service.domain.TicketService;
import com.railway.ticket_sale_service.persistence.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ticket")
public class TicketRestController implements BookTicketListener {
    private static Logger logger = LoggerFactory.getLogger(TicketRestController.class);

    private TicketRepository ticketRepository;
    private final Map<Long, DeferredResult<Ticket>> deferredResults;
    private final TicketService ticketService;

    @Autowired
    public TicketRestController(TicketRepository ticketRepository, TicketService ticketService) {
        this.ticketRepository = ticketRepository;
        this.deferredResults = new HashMap<>(10);
        this.ticketService = ticketService;
    }

    @PostConstruct
    private void registerListener() {
        ticketService.registerListener(this);
    }

    @GetMapping
    public Iterable<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable("id") Long id){
        return ticketRepository.findById(id).orElse(null);
    }

    @PostMapping()
    public DeferredResult<Ticket> buyTicket (@RequestBody TicketRequest ticketRequest){
        logger.info("[Ticket Sale Rest Controller] buy ticket");

        DeferredResult<Ticket> deferredResult = new DeferredResult<>(10000l);

        if(!isValidTicketRequest(ticketRequest)){
            deferredResult.setErrorResult("Request must contain the following fields in the body; \"startStationId\", \"endStationId\"" +
                    ", \"startDateTime\", \"amountOfSeats\" and \"routeId\"");
        }

        deferredResult.onTimeout(() -> {
            deferredResult.setErrorResult("Request timeout occurred");
        });

        Ticket ticket = new Ticket(ticketRequest.getStartDateTime(), ticketRequest.getAmountOfSeats());
        ticketRepository.save(ticket);
        deferredResults.put(ticket.getId(), deferredResult);
        ticketService.fetchRouteDetails(ticket, ticketRequest);
        return deferredResult;
    }

    private boolean isValidTicketRequest(TicketRequest ticketRequest){
        return ticketRequest.getStartStationId() != null && ticketRequest.getEndStationId() != null;
    }

    private void performResponse(Ticket ticket){
        DeferredResult<Ticket> deferredResult = this.deferredResults.remove(ticket.getId());
        if (deferredResult != null && !deferredResult.isSetOrExpired())
            deferredResult.setResult(ticket);
        else
            logger.info("[Ticket Sale Rest Controller] defereredResult: " + deferredResult);
    }

    @Override
    public void onBookTicketListener(Ticket ticket) {
        this.performResponse(ticket);
    }
}
