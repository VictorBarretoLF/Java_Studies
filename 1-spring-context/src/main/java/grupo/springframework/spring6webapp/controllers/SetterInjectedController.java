package grupo.springframework.spring6webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import grupo.springframework.spring6webapp.services.GreetingService;

@Controller
public class SetterInjectedController {


    private GreetingService greetingService;

    @Qualifier("setterGreetingBean")
    @Autowired
    public void setGreetingService(GreetingService greetingService) {
        System.out.println("SetterInjectedController.setGreetingService");
    }

    public String sayHello(){
        return greetingService.sayGreeting();
    }
}
