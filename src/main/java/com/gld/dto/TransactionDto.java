package com.gld.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.gld.common.TransactionType;

public record TransactionDto
(
	
	Long id,
	LocalDateTime txDate,	
	List<String> products,
	String remark,
	TransactionType type,
	BigDecimal amount,
	BigDecimal balance,
	PartyDto partyDto
) 

{}
