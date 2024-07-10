package com.casestudy.userauth.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.casestudy.userauth.model.Users;

public interface UserRepository extends MongoRepository<Users, String> {
	
	Optional<Users> findByUserName(String userName);
}
