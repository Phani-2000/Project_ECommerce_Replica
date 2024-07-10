package com.casestudy.userauth.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				title = "Authentication Service",
				description = "This service is responsible for registering and auhtenticating the users",
				version = "v1"), 
		servers = {
				@Server(
						description = "DEV", 
						url = "http://localhost:8111/"
				)}
		)
public class OpenApiConfig {

}
