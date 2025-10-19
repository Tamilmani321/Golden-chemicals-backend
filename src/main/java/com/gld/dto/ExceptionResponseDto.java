package com.gld.dto;

import org.springframework.http.HttpStatus;

public record ExceptionResponseDto
(
		String msg, HttpStatus status, int statusCode
) 

{}
