package com.gld.service;

import java.util.List;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.gld.dto.PartyDto;

@EnableJpaRepositories
public interface PartyService 
{

	PartyDto save(PartyDto dto);

	PartyDto getParty(Long id);

	List<PartyDto> getParties();
	
	
}
