package com.casestudy.shoppingcart.testconfigs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.casestudy.shoppingcart.security.JwtAuthEntryPoint;
import com.casestudy.shoppingcart.security.JwtAuthFilter;

@TestConfiguration
@EnableWebSecurity
public class SecurityConfigTest {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().permitAll()
        .and().csrf().disable();
		
		return http.build();
	}
}

