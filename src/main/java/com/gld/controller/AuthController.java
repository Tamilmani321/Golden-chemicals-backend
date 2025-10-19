package com.gld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gld.customException.ResourceNotFound;
import com.gld.dao.UserRepo;
import com.gld.dto.AuthRequestDto;
import com.gld.dto.AuthResponseDto;
import com.gld.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {

		try {
			log.info(" login Request Data : " + request);
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
			AuthResponseDto responseDto = new AuthResponseDto(jwtUtil.generateToken(authentication.getName()),
					authentication.getName(),HttpStatus.OK.value());
			log.info(" login Response Data : " + responseDto);
			return ResponseEntity.status(HttpStatus.OK).body(responseDto);
		} catch (BadCredentialsException e) {
			log.warn(" BadCredentials : " + e.getMessage());
			throw new ResourceNotFound("Invalid Credentials");
		}
		
		
	}

	
	


}
