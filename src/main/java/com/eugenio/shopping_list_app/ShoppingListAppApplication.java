package com.eugenio.shopping_list_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ShoppingListAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingListAppApplication.class, args);
    }

}
