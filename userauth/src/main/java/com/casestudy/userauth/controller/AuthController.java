package com.casestudy.userauth.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.userauth.controller.dto.LoginDto;
import com.casestudy.userauth.controller.dto.RegisterDto;
import com.casestudy.userauth.model.Users;
import com.casestudy.userauth.repository.UserRepository;
import com.casestudy.userauth.security.JwtGenerator;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtGenerator jwtGenerator;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
		Optional<Users> user=userRepository.findByUserName(registerDto.getUsername());
		if(user.isPresent()) {
			return new ResponseEntity<String>("User already exists", HttpStatus.BAD_REQUEST);
		}
		Users registerUser=new Users();
		registerUser.setUserName(registerDto.getUsername());
		registerUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		List<String> roles=Arrays.asList("User","Admin");
		registerUser.setRoles(roles);
		
		userRepository.save(registerUser);
		return new ResponseEntity<String>("User registered", HttpStatus.OK);
		
	}
	
	@PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt=jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>("User logged in successfully. JWT is: "+jwt, HttpStatus.OK);
    }

}
