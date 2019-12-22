package com.railway.delay_service.adapters.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.railway.delay_service.adapters.messaging.DelayRequest;
import com.railway.delay_service.domain.DelayService;


@RestController
@RequestMapping("/delay")
public class DelayRestController {
	
	private DelayService delayService;
	
	public DelayRestController(DelayService delayService) {
		this.delayService = delayService;
	}
	
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@PostMapping(consumes = "application/json")
	public void postDelay (@RequestBody DelayRequest delay) {
		this.delayService.sendDelay(delay);
	}
}
