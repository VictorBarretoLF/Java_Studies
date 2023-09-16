package grupo.springframework.spring6webapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import grupo.springframework.spring6webapp.services.GreetingServiceImpl;

@SpringBootTest
public class SetterInjectedControllerTest {
    SetterInjectedController setterInjectedController;

    @BeforeEach
    void setUp() {
        setterInjectedController = new SetterInjectedController();
        setterInjectedController.setGreetingService(new GreetingServiceImpl());

    }

    @Test
    void sayHello() {
        System.out.println(setterInjectedController.sayHello());
    }
}
