package Microservice.Token_Service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

//Swagger Configuration class for Documenting APIs

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI inventoryServiceApi() {
		return new OpenAPI()
				.info(new Info().title("Token Service API")
						.description("This Service is Dedicated for Generating JWT Token and Validating Token")
						.version("v0.0.1")
						.license(new License().name("Apache 2.0")))
				.externalDocs(new ExternalDocumentation()
						.description("Refer this Docs for More Information about APIs")
						.url("http://token-service-api.docs"));//Dummy url given 
				
	}
}
