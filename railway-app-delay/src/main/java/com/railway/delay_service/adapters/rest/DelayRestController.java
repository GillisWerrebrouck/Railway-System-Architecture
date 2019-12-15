package com.railway.delay_service.adapters.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railway.delay_service.adapters.messaging.DelayRequest;


@RestController
@RequestMapping("/delay")
public class DelayRestController {

	@PostMapping(consumes = "application/json")
	public void postDelay (@RequestBody DelayRequest delay) {
		
		
	}
}
