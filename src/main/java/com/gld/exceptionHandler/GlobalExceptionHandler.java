package com.gld.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gld.customException.ResourceNotFound;
import com.gld.dto.ExceptionResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler  extends RuntimeException {
	
	 @ExceptionHandler(ResourceNotFound.class)
	    public ResponseEntity<ExceptionResponseDto> handleResourceNotFound(ResourceNotFound ex) {
		 ExceptionResponseDto error = new ExceptionResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value());
	        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	    }

}
