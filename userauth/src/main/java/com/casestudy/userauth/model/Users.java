package com.casestudy.userauth.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class Users {
	
	@Id
	private String userId;
	private String userName;
	private String password;
	private List<String> roles;

}
