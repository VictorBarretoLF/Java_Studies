package grupo.springframework.spring6webapp.controllers;

import org.springframework.stereotype.Controller;

import grupo.springframework.spring6webapp.services.EnvironmentService;

@Controller
public class EnvironmentController {

    private final EnvironmentService environmentService;

    public EnvironmentController(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    public String getEnvironment(){
        return "You are in " + environmentService.getEnv() + " Environment";
    }
}
