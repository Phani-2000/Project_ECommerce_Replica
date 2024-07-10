package com.casestudy.userauth.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.casestudy.userauth.model.Users;
import com.casestudy.userauth.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user= userRepository.findByUserName(username).orElseThrow(()-> new UsernameNotFoundException("Couldn't find the user"));
		return new User(user.getUserName(), user.getPassword(), mapToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapToAuthorities(List<String> roles) {
		return roles.stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toList());
	}

	

}
