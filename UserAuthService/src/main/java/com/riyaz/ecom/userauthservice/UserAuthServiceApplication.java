package com.riyaz.ecom.userauthservice;

import com.riyaz.ecom.userauthservice.models.User;
import com.riyaz.ecom.userauthservice.repositories.UserRepository;
import com.riyaz.ecom.userauthservice.services.IAuthService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class UserAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserAuthServiceApplication.class, args);
    }

}
