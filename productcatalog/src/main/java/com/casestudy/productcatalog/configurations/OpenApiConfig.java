package com.casestudy.productcatalog.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				title = "Product-Catalog Service",
				description = "This service maintains the TeleCom Product Inventory",
				version = "v1"), 
		servers = {
				@Server(
						description = "DEV", 
						url = "http://localhost:8082/"
				)},
		security = @SecurityRequirement(name = "auth")
		)
@SecurityScheme(
		name = "auth",
		in = SecuritySchemeIn.HEADER,
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "Bearer")
public class OpenApiConfig {

}
