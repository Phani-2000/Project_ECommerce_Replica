package com.casestudy.userauth.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtGenerator {
	@Value("${app.secret.key}")
	private String key;
	@Value("${app.expiration.time}")
	private String expirationTime;
	
	public String generateToken(Authentication auth) {
		
		String username=auth.getName();
		
		Date current=new Date();
		
		Date expDate=new Date(current.getTime()+ Long.parseLong(expirationTime));
		
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(current)
				.setExpiration(expDate)
				.signWith(SignatureAlgorithm.HS512, key)
				.compact();
	}
}
