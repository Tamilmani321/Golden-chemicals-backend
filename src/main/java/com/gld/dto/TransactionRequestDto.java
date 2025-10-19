package com.gld.dto;

import java.time.LocalDateTime;

public record TransactionRequestDto
(
	Long pId,
	String type,
	LocalDateTime fromDate,
	LocalDateTime toDatess
		
) 
{}
