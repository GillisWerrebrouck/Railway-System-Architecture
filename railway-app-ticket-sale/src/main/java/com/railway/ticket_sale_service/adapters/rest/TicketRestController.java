package com.railway.ticket_sale_service.adapters.rest;

import com.railway.ticket_sale_service.domain.*;
import com.railway.ticket_sale_service.persistence.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/ticket")
public class TicketRestController implements BookTicketListener {

    private static Logger logger = LoggerFactory.getLogger(TicketRestController.class);

    private TicketRepository ticketRepository;
    private final Map<UUID, DeferredResult<List<Ticket>>> deferredResults;
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
    public DeferredResult<List<Ticket>> buyTicket (@RequestBody TicketRequest ticketRequest){
        logger.info("[Ticket Sale Rest Controller] buy ticket");

        DeferredResult<List<Ticket>> deferredResult = new DeferredResult<>(10000l);

        if(!isValidTicketRequest(ticketRequest)){
            deferredResult.setErrorResult("Request must contain the following fields in the body; \"startStationId\", \"endStationId\"" +
                    ", \"timeTableId\", \"amountOfSeats\" and \"ticketType\"");
        }

        deferredResult.onTimeout(() -> {
            deferredResult.setErrorResult("Request timeout occurred");
        });

        deferredResults.put(ticketRequest.getTicketCreationId(), deferredResult);
        ticketService.bookTickets(ticketRequest);
        return deferredResult;
    }

    private boolean isValidTicketRequest(TicketRequest ticketRequest){
        return ticketRequest.getStartStationId() != null && ticketRequest.getStartDateTime() != null && ticketRequest.getEndStationId() != null && ticketRequest.getTimetableId() != null;
    }

    private void performResponse(List<Ticket> tickets){
        DeferredResult<List<Ticket>> deferredResult = this.deferredResults.remove(tickets.get(0).getTicketCreationId());
        if (deferredResult != null && !deferredResult.isSetOrExpired())
            deferredResult.setResult(tickets);
        else
            logger.info("[Ticket Sale Rest Controller] defereredResult: " + deferredResult);
    }

    private void performError(List<Ticket> tickets, String errorMessage){
        DeferredResult<List<Ticket>> deferredResult = this.deferredResults.remove(tickets.get(0).getTicketCreationId());
        if (deferredResult != null && !deferredResult.isSetOrExpired())
            deferredResult.setErrorResult("Failed to create " + tickets.size() + " ticket(s). " + errorMessage);
        logger.info("[Ticket Sale Rest Controller] defereredResult: " + deferredResult);
    }

    @Override
    public void onBookTicketResult(List<Ticket> tickets) {
        performResponse(tickets);
    }

    @Override
    public void onBookTicketError(List<Ticket> tickets, String errorMessage) {
        performError(tickets, errorMessage);
    }
}
