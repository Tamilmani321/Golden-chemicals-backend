package com.gld.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gld.customException.ResourceNotFound;
import com.gld.dao.UserRepo;


@Service
public class CustomerUserDetailService implements UserDetailsService {

	 @Autowired
	 private UserRepo userRepo; 

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    	com.gld.entity.User user = userRepo.findByName(username)
	            .orElseThrow(() -> new ResourceNotFound("Invalid Credentials"));

	        return User.withUsername(user.getName())
	                .password(user.getPassword()) 
	                .roles("")
	                .build();
	    }
	}
