package com.gld.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil 
{
	private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	private static final int TEN_HOUR = 1000 * 60 * 60 * 10;
	
	public String generateToken(String username) {
		Date now = new Date();
        Date expiry = new Date(now.getTime() + TEN_HOUR);
		
	    return Jwts.builder()
	            .setSubject(username)
	            .setIssuedAt(now)
	            .setExpiration(expiry) // 10 hr expiry
	            .signWith(key)
	            .compact();
	}
	
	public boolean validateToken(String token, String username) {
	    String tokenUser = extractUsername(token);
	    return (tokenUser.equals(username) && !isTokenExpired(token));
	}
	
	public String extractUsername(String token) {
	    return Jwts.parserBuilder().setSigningKey(key).build()
	            .parseClaimsJws(token).getBody().getSubject();
	}
	
	private boolean isTokenExpired(String token) {
	    Date expiration = Jwts.parserBuilder().setSigningKey(key).build()
	            .parseClaimsJws(token).getBody().getExpiration();
	    return expiration.before(new Date());
	}


}
