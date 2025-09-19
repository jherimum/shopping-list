package com.eugenio.shopping_list_app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI shoppingListOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Shopping List API")
                        .description("REST API for managing shopping lists")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Eugenio")
                                .email("eugenio@example.com")));
    }
}