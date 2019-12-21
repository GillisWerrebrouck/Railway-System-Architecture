package com.railway.timetable_service.adapters.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.railway.timetable_service.RailwayAppTimetableApplication;
import com.railway.timetable_service.adapters.messaging.Route;
import com.railway.timetable_service.domain.CreateTimetableItemListener;
import com.railway.timetable_service.domain.Status;
import com.railway.timetable_service.domain.TimetableItem;
import com.railway.timetable_service.domain.TimetableRequest;
import com.railway.timetable_service.domain.TimetableService;
import com.railway.timetable_service.persistence.TimetableItemRepository;

@RestController
@RequestMapping("/timetable")
public class TimetableItemRestController implements CreateTimetableItemListener {
	private static Logger logger = LoggerFactory.getLogger(RailwayAppTimetableApplication.class);
	
	private TimetableItemRepository timetableItemRepository;
	private final Map<Long, DeferredResult<TimetableItem>> deferredResults;
	private TimetableService timetableService;
	
	@Autowired
	private TimetableItemRestController(TimetableItemRepository timetableItemRepository, TimetableService timetableService) {
		this.timetableItemRepository = timetableItemRepository;
		this.deferredResults = new HashMap<>(10);
		this.timetableService = timetableService;
	}
	
	@PostConstruct
	private void registerListener() {
		timetableService.registerCreateTimetableListener(this);
	}
	
	@GetMapping("/all")
	private Collection<TimetableItemResponse> getAllTimetableItems() {
		Iterator<TimetableItem> timetableItems = timetableItemRepository.findAll().iterator();
		Collection<TimetableItemResponse> response = new ArrayList<>();
		
		while (timetableItems.hasNext()) {
			TimetableItem timetableItem = timetableItems.next();
			
			String routeName = timetableService.getRouteName(timetableItem.getRouteId());
			
			TimetableItemResponse timetableItemResponse = new TimetableItemResponse(timetableItem.getId(), timetableItem.getStartDateTime(), timetableItem.getEndDateTime(), timetableItem.getDelay(), timetableItem.getRouteId(), routeName);
			response.add(timetableItemResponse);
		}
		
		return response;
	}
	
	@GetMapping("/{id}")
	private TimetableItem getTimetableItemById(@PathVariable Long id) {
		TimetableItem timetableItem = timetableItemRepository.findById(id).orElse(null);
		
		return timetableItem;
	}
	
	@GetMapping
	private Collection<TimetableItemResponse> getTimetableItemsByStartAndEndStation(@RequestParam UUID startStationId, @RequestParam UUID endStationId,
			@RequestParam String fromDate, @RequestParam String toDate) throws Exception {
		Collection<Route> routes = timetableService.getRoutes(startStationId, endStationId);
		Collection<TimetableItemResponse> response = new ArrayList<>();
		
		for(Route route : routes) {
			try {
				LocalDateTime fromDateTime = LocalDateTime.parse(fromDate);
				LocalDateTime toDateTime = LocalDateTime.parse(toDate);

				Collection<TimetableItem> timetableItems = timetableItemRepository.findByRouteIdAndStartDateTimeBetweenOrderByStartDateTime(route.getId(), fromDateTime, toDateTime);
				
				for(TimetableItem timetableItem : timetableItems) {
					TimetableItemResponse timetableItemResponse = new TimetableItemResponse(timetableItem.getId(), timetableItem.getStartDateTime(), timetableItem.getEndDateTime(), timetableItem.getDelay(), timetableItem.getRouteId(), route.getName());
					response.add(timetableItemResponse);
				}
			} catch (DateTimeParseException e) {
				throw new Exception("Could not convert request parameters to correct type: " + e.getMessage());
			}
		}
		
		return response;
	}
	
	@GetMapping("/{timetableId}/route")
	private Collection<ScheduleItemResponse> getStationsByTimetableItemId(@PathVariable Long timetableId) {
		return timetableService.getStationByTimetableItemId(timetableId);
	}
	
	@GetMapping("/route/{routeId}")
	private Iterable<TimetableItem> getTimetableItemByRouteId(@PathVariable Long routeId) {
		return timetableItemRepository.findByRouteId(routeId);
	}
	
	@PostMapping
	private DeferredResult<TimetableItem> createTimetableItem(@RequestBody TimetableRequest timetableRequest) {
		logger.info("[Timetable Item Rest Controller] create timetable item");
		
		DeferredResult<TimetableItem> deferredResult = new DeferredResult<>(10000l);
		
		if(!isValidTimetableRequest(timetableRequest)) {
			deferredResult.setErrorResult("Request must contain the following fields in the body; \"routeId\", \"startDateTime\" and \"requestedTrainType\"");
		}
		
		deferredResult.onTimeout(() -> {
			deferredResult.setErrorResult("Request timeout occurred");
		});
		
		TimetableItem timetableItem = new TimetableItem(timetableRequest.getRouteId(), timetableRequest.getStartDateTime(), timetableRequest.getRequestedTrainType());
		
		timetableItemRepository.save(timetableItem);
		
		this.deferredResults.put(timetableItem.getId(), deferredResult);
		
		this.timetableService.createTimetableItem(timetableItem, timetableRequest);
		
		return deferredResult;
	}

	private boolean isValidTimetableRequest(TimetableRequest request) {
		return request.getRouteId() != null && request.getStartDateTime() != null && request.getRequestedTrainType() != null;
	}

	private void performSuccessfulResponse(TimetableItem timetableItem) {
		DeferredResult<TimetableItem> deferredResult = this.deferredResults.remove(timetableItem.getId());
		if (deferredResult != null && !deferredResult.isSetOrExpired()) {
			deferredResult.setResult(timetableItem);
		} else {
			logger.info("defereredResult: " + deferredResult);
		}
	}

	@Override
	public void onCreateTimetableItemResult(TimetableItem timetableItem) {
		logger.info("[Timetable Item Rest Controller] succefully created a timetable item");
		this.performSuccessfulResponse(timetableItem);
	}

	private void performFailedResponse(TimetableItem timetableItem) {
		DeferredResult<TimetableItem> deferredResult = this.deferredResults.remove(timetableItem.getId());
		if (deferredResult != null && !deferredResult.isSetOrExpired()) {			
			if (timetableItem.getRouteStatus() == Status.FAILED) {
				deferredResult.setErrorResult("Failed to create timetable item: route could not be fetched");
			} else if (timetableItem.getTrainReservationStatus() == Status.FAILED) {
				deferredResult.setErrorResult("Failed to create timetable item: train could not be reserved");
			} else if (timetableItem.getStationsReservationStatus() == Status.FAILED) {
				deferredResult.setErrorResult("Failed to create timetable item: stations on route could not be reserved");
			} else if (timetableItem.getStaffReservationStatus() == Status.FAILED) {
				deferredResult.setErrorResult("Failed to create timetable item: staff could not be reserved");
			} else {
				deferredResult.setErrorResult("Failed to create timetablle item: unknown cause");
			}
		} else {
			logger.info("defereredResult: " + deferredResult);
		}
	}

	@Override
	public void onCreateTimetableItemFailed(TimetableItem timetableItem) {
		logger.info("[Timetable Item Rest Controller] failed to create a timetable item");
		this.performFailedResponse(timetableItem);
	}
}
