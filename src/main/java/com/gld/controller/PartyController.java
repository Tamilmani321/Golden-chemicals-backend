package com.gld.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gld.dto.PartyDto;
import com.gld.service.PartyService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gmd/party")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class PartyController {
	
	@Autowired
	private PartyService partyService;
	
	@PostMapping
	public ResponseEntity<PartyDto> save(@RequestBody @Validated PartyDto dto) {
		log.info(" Requested Data Saving  : "+dto);
		PartyDto responseDto = partyService.save(dto);
		log.info(" Response Data Saving  : "+responseDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
	}
	
	@GetMapping("/{pId}")
	public ResponseEntity<PartyDto> get (@PathVariable Long pId){
		log.info(" Requested Data Fetching By Id  : "+pId);
		PartyDto responseDto = partyService.getParty(pId);
		log.info(" Response Data Fetching By Id  : "+responseDto);
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}
	
	@GetMapping
	public ResponseEntity<List<PartyDto>> getAll (){
		List<PartyDto> responseDtos = partyService.getParties();
		log.info(" Response Data Fetching All Data  : "+responseDtos);
		return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
	}
}
