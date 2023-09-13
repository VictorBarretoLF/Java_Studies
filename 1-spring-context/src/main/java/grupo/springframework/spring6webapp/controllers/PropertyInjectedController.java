package grupo.springframework.spring6webapp.controllers;

import grupo.springframework.spring6webapp.services.GreetingService;

public class PropertyInjectedController {

	GreetingService greetingService;
	
	public String sayHello() {
		return greetingService.sayGreeting();
	}
}
