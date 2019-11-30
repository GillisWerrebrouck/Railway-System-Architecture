package com.railway.station_service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/informationpanel")
public class InformationPanelRestController {

	private final InformationPanelRepository informationPanelRepository;

	@Autowired
	public InformationPanelRestController(InformationPanelRepository informationPanelRepository) {
		this.informationPanelRepository = informationPanelRepository;
	}

	@GetMapping
	public Iterable<InformationPanel> getStations(){
		return this.informationPanelRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<InformationPanel> informationPanelById(@PathVariable int id) {
		Optional <InformationPanel> optInformationPanel = informationPanelRepository.findById(id);
		if(optInformationPanel.isPresent()) {
			return new ResponseEntity<>(optInformationPanel.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public InformationPanel postInformationPanel (@RequestBody InformationPanel ip) {
		return informationPanelRepository.save(ip);
	}
	
	@DeleteMapping("/{id}")
	public void deleteInformationPanel(@PathVariable int id) {
		informationPanelRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<InformationPanel> updateInformationPanel(@RequestBody InformationPanel informationPanel, @PathVariable int id) {

		Optional<InformationPanel> informationPanelOptional = informationPanelRepository.findById(id);

		if (!informationPanelOptional.isPresent())
			return ResponseEntity.notFound().build();

		informationPanel.setId(id);
		
		informationPanelRepository.save(informationPanel);

		return ResponseEntity.noContent().build();
	}

}
