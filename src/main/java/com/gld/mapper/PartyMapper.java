package com.gld.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.gld.dto.PartyDto;
import com.gld.entity.Party;

@Mapper(componentModel = "spring")
public interface PartyMapper {
	
	PartyDto entityToDto(Party party);

	Party dtoToEntity(PartyDto partyDto);
	
	List<PartyDto> entityListToDtoList (List<Party> parties);
	
	List<Party> dtoListToEntityList (List<PartyDto> partyDtos);

}
