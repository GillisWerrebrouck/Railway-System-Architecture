package com.railway.delay_service.adapters.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.railway.delay_service.DelayService;
import com.railway.delay_service.adapters.messaging.DelayRequest;


@RestController
@RequestMapping("/delay")
public class DelayRestController {
	
	//private final Map<String, DeferredResult<DelayRequest>> deferredResults;
	private DelayService delayService;
	
	public DelayRestController(DelayService delayService) {
		//this.deferredResults = new HashMap<>(10);
		this.delayService = delayService;
	}
	
	@PostMapping(consumes = "application/json")
	public void postDelay (@RequestBody DelayRequest delay) {
		this.delayService.sendDelay(delay);
	}
}
