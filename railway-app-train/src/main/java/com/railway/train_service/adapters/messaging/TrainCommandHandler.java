package com.railway.train_service.adapters.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.railway.train_service.domain.Train;
import com.railway.train_service.domain.TrainService;

@Service
public class TrainCommandHandler {
	private static Logger logger = LoggerFactory.getLogger(TrainCommandHandler.class);
	private final TrainService trainService;

	@Autowired
	public TrainCommandHandler(TrainService trainService) {
		this.trainService = trainService;
	}
	
	@StreamListener(Channels.RESERVE_TRAIN)
	@SendTo(Channels.TRAIN_RESERVED)
	public TrainResponse reserveTrain(TrainRequest request) {
		logger.info("[Train Command Handler] reserve train command received");
		Train train = trainService.reserveTrain(request.getTimetableId(), request.getStartDateTime(), request.getEndDateTime(), request.getTrainType());
		TrainResponse response;
		if(train != null) {
			logger.info("[Train Command Handler] train reserved");
			response = new TrainResponse(train.getId(), request.getTimetableId(), request.getRequestId());
		} else {
			logger.info("[Train Command Handler] no train could be reserved");
			response = new TrainResponse(null, request.getTimetableId(), request.getRequestId());
		}
		return response;
	}
	
	@StreamListener(Channels.DISCARD_TRAIN_RESERVATION)
	public void discardTrainReservation(DiscardReservationRequest request) {
		logger.info("[Train Command Handler] discard train reservation command received");
		trainService.discardTrainReservation(request.getTimetableId());
	}
}
