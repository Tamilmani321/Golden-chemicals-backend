package com.gld.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import com.gld.dto.TransactionDto;
import com.gld.entity.Transaction;

@Mapper(componentModel = "spring", uses = { PartyMapper.class })
public interface TransactionMapper {

	@Mapping(source = "partyDto", target = "party")
    Transaction dtoToEntity(TransactionDto transactionDto);
    
    @Mapping(source = "party", target = "partyDto")
    TransactionDto entityToDto(Transaction transaction);

    List<Transaction> listDtoToListEntity(List<TransactionDto> transactionDtos);

    List<TransactionDto> listEntityToListDto(Page<Transaction> transactions);
}
