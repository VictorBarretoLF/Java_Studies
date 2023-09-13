package grupo.springframework.spring6webapp.controllers;

import org.junit.jupiter.api.Test;

public class MyControllerTeste {

	@Test
	void sayHello() {
		MyController myController = new MyController();
		
		System.out.println(myController.sayHello());
	}
}
