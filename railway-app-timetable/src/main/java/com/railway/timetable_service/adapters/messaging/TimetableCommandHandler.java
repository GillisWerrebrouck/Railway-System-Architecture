package com.railway.timetable_service.adapters.messaging;

import com.railway.timetable_service.domain.NotEnoughGroupSeatsException;
import com.railway.timetable_service.domain.TimetableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class TimetableCommandHandler {
    private static Logger logger = LoggerFactory.getLogger(TimetableEventHandler.class);
    private final TimetableService timetableItemService;
    private final MessageGateway gateway;

    @Autowired
    public TimetableCommandHandler(TimetableService timetableItemService, MessageGateway gateway) {
        this.timetableItemService = timetableItemService;
        this.gateway = gateway;
    }

    @StreamListener(Channels.RESERVE_GROUP_SEATS)
    @SendTo(Channels.GROUP_SEATS_RESERVED)
    public ReserveGroupSeatsResponse reserveGroupSeats(GroupSeatsRequest groupSeatsRequest){
        try {
            timetableItemService.reserveGroupSeats(groupSeatsRequest);
            logger.info("[Timetable Event Handler] successfully reserved group seats");
            return new ReserveGroupSeatsResponse(groupSeatsRequest.getTicketId(), groupSeatsRequest.getTimeTableId(), true, groupSeatsRequest.getGroupSeatsRequestId());
        } catch (NotEnoughGroupSeatsException e) {
            logger.info("[Timetable Event Handler] failed to reserve group seats, not enough group seats available");
            return new ReserveGroupSeatsResponse(groupSeatsRequest.getTicketId(), groupSeatsRequest.getTimeTableId(), false, groupSeatsRequest.getGroupSeatsRequestId());
        } catch (NullPointerException en){
            logger.info("[Timetable Event Handler] no timetableItem found with the id " + groupSeatsRequest.getTimeTableId());
            return new ReserveGroupSeatsResponse(groupSeatsRequest.getTicketId(), null, false, groupSeatsRequest.getGroupSeatsRequestId());
        }
    }

    @StreamListener(Channels.DISCARD_RESERVED_SEATS)
    public void discardReservedGroupSeats(GroupSeatsRequest groupSeatsRequest){
        logger.info("[Timetable Event Handler] discard reserved group seats");
        timetableItemService.discardReservedGroupSeats(groupSeatsRequest.getTimeTableId(), groupSeatsRequest.getAmountOfSeats());
    }
    
    @StreamListener(Channels.CHECK_ROUTE_USAGE)
    @SendTo(Channels.ROUTE_USAGE_CHECKED)
    public RouteUsageResponse checkRouteUsage(RouteUsageRequest request){
        logger.info("[Timetable Command Handler] check route usage command received");
        boolean isUsed = timetableItemService.areRoutesUsed(request.getRouteIds());
        return new RouteUsageResponse(request.getConnectionId(), isUsed);
    }
  
    @StreamListener(Channels.NOTIFY_DELAY)
    public void notifyDelay(DelayRequest request) {
      logger.info("[Timetable Command Handler] notify delay command received");
      timetableService.processDelay(request);
    }

    @StreamListener(Channels.NOTIFY_EXTRA_DELAY)
    public void notifyExtraDelay(UpdateDelayRequest request) {
      logger.info("[Timetable Command Handler] notify extra delay command received");
      timetableService.processExtraDelay(request);
    }
}
