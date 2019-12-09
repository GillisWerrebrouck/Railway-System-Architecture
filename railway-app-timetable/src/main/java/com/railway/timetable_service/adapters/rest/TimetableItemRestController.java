package com.railway.timetable_service.adapters.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.railway.timetable_service.RailwayAppTimetableApplication;
import com.railway.timetable_service.domain.CreateTimetableItemListener;
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
	
	@GetMapping
	private Iterable<TimetableItem> getAllTimetableItems() {
		return timetableItemRepository.findAll();
	}
	
	@GetMapping("/route/{routeId}")
	private Iterable<TimetableItem> getTimetableItemByRouteId(@PathVariable Long routeId) {
		return timetableItemRepository.findByRouteId(routeId);
	}
	
	@GetMapping("/{id}")
	private Optional<TimetableItem> getTimetableItemById(@PathVariable Long id) {
		return timetableItemRepository.findById(id);
	}
	
	@PostMapping
	private DeferredResult<TimetableItem> createTimetableItem(@RequestBody TimetableRequest timetableRequest) {
		logger.info("[Timetable Item Rest Controller] create timetable item");
		DeferredResult<TimetableItem> deferredResult = new DeferredResult<>(10000l);
		
		deferredResult.onTimeout(() -> {
			deferredResult.setErrorResult("Request timedout occurred");
		});
		logger.info(timetableRequest.getRequestedTrainType().toString());
		TimetableItem timetableItem = new TimetableItem(timetableRequest.getRouteId(), timetableRequest.getStartDateTime(), timetableRequest.getRequestedTrainType());
		
		timetableItemRepository.save(timetableItem);
		
		this.deferredResults.put(timetableItem.getId(), deferredResult);

		try {
			this.timetableService.createTimetableItem(timetableItem, timetableRequest);
		} catch (Exception e) {
			logger.info("[Timetable Item Rest Controller] failed to create a timetable item");
			deferredResult.setErrorResult("Failed to create timetablle item. " + e.getMessage());
			this.deferredResults.remove(timetableItem.getId());
		}
		
		logger.info("[Timetable Item Rest Controller] succefully created a timetable item");
		return deferredResult;
	}

	private void performResponse(TimetableItem timetableItem) {
		DeferredResult<TimetableItem> deferredResult = this.deferredResults.remove(timetableItem.getId());
		if (deferredResult != null && !deferredResult.isSetOrExpired()) {
			deferredResult.setResult(timetableItem);
		} else {
			System.out.println("defereredResult: " + deferredResult);
		}
	}

	@Override
	public void onCreateTimetableItemResult(TimetableItem timetableItem) {
		this.performResponse(timetableItem);
	}
}
