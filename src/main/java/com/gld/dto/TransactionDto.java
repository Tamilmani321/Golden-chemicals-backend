package com.gld.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.gld.common.TransactionType;

public record TransactionDto
(
	
	Long id,
	LocalDateTime txDate,	
	String product,
	String remark,
	TransactionType type,
	BigDecimal amount,
	BigDecimal balance,
	PartyDto partyDto
) 

{}
