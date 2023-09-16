package grupo.springframework.spring6webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import grupo.springframework.spring6webapp.services.GreetingService;

@Controller
public class PropertyInjectedController {

    @Qualifier("propertyGreetingService")
    @Autowired
    GreetingService greetingService;

    public String sayHello(){
        return greetingService.sayGreeting();
    }

}
