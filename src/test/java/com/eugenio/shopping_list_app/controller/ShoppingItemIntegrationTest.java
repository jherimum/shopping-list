package com.eugenio.shopping_list_app.controller;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WebAppConfiguration
public class ShoppingItemIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;


}
