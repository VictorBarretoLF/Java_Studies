package grupo.springframework.spring6webapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import grupo.springframework.spring6webapp.services.GreetingServiceImpl;

@SpringBootTest
public class PropertyInjectedControllerTest {
	
	PropertyInjectedController propertyInjectedController;
	
	@BeforeEach
	void setUp() {
		propertyInjectedController = new PropertyInjectedController();
		propertyInjectedController.greetingService = new GreetingServiceImpl();
	}
	
	@Test
	void sayHello() {
		System.out.println(propertyInjectedController.sayHello());
	}

}
