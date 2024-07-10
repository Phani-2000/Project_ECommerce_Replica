package com.casestudy.orderservcie.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtAuthEntryPoint authEntryPoint;
	
	@Autowired
	private JwtAuthFilter authFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.exceptionHandling()
        .authenticationEntryPoint(authEntryPoint)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
		.authorizeRequests()
		.requestMatchers("/actuator/**").permitAll()
		.requestMatchers(AUTH_SWAGGER_URLS).permitAll()
		.anyRequest().authenticated()
		.and()
		.httpBasic();
		
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	private static final String[] AUTH_SWAGGER_URLS= {
			"/api/v1/auth/**",
			"/v3/api-docs/**", 
			"/v3/api-docs.yaml",
			"/swagger-ui/**", 
			"/swagger-ui.html"			
	};
	

}