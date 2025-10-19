package com.gld.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gld.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserDetailsService userDetailsService;
	
	private final static int TOKEN_PRFIX_LENGTH = 7;

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");

		try {
			if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
				String Jwttoken = authHeader.substring(TOKEN_PRFIX_LENGTH);
				String username = jwtUtil.extractUsername(Jwttoken);

				if (username != null && !username.isBlank()
						&& SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);

					if (jwtUtil.validateToken(Jwttoken, userDetails.getUsername())) {
						UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
								null, userDetails.getAuthorities());
						SecurityContextHolder.getContext().setAuthentication(authToken);

					}
				}

			}
			
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		filterChain.doFilter(request, response);
		return;
		

	}

}
