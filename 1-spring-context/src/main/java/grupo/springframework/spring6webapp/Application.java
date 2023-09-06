package grupo.springframework.spring6webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import grupo.springframework.spring6webapp.controllers.MyController;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		
		MyController controller = ctx.getBean(MyController.class);
		
		System.out.println("In Main Method");
		
		System.out.println(controller.sayHello());
	}

}
