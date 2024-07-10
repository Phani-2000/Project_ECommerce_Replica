package com.casestudy.productcatalog.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtValidator {
	
	@Value("${app.secret.key}")
	private String key;
	
	public Claims getClaimsFromJWT(String token){
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims;
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token);
			return true;
		} catch (Exception ex) {
			throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect",ex.fillInStackTrace());
		}
	}
}
