package grupo.springframework.spring6webapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import grupo.springframework.spring6webapp.services.GreetingServiceImpl;

public class ConstructorInjectedControllerTest {
    ConstructorInjectedController controller;

    @BeforeEach
    void setUp() {
        controller = new ConstructorInjectedController(new GreetingServiceImpl());
    }

    @Test
    void sayHello() {
        System.out.println(controller.sayHello());

    }
}
