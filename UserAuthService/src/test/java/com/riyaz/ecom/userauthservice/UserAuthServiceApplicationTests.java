package com.riyaz.ecom.userauthservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.SpringApplication;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

@SpringBootTest(properties = "jwt.secret=MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=")
class UserAuthServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void mainDelegatesArgumentsToSpringApplication() {
        String[] args = {"--spring.main.web-application-type=none"};

        try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class)) {
            UserAuthServiceApplication.main(args);

            springApplication.verify(() -> SpringApplication.run(UserAuthServiceApplication.class, args), times(1));
        }
    }

}
