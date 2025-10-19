package com.gld.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gld.dao.PartyRepo;
import com.gld.dto.PartyDto;
import com.gld.mapper.PartyMapper;
import com.gld.service.PartyService;

@Service
public class PartyServiceImpl implements PartyService
{
	@Autowired
	private PartyRepo partyRepo;
	
	@Autowired
	private PartyMapper PM;
	
	@Override
	public PartyDto save(PartyDto dto) {
		return PM.entityToDto(partyRepo.save(PM.dtoToEntity(dto)));
	}

	@Override
	public PartyDto getParty(Long id) {
		
		return null;
	}

	@Override
	public List<PartyDto> getParties() {
		return PM.entityListToDtoList(partyRepo.findAll(Sort.by("name")));
	}

}
