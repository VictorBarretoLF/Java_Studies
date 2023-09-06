package grupo.springframework.spring6webapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import grupo.springframework.spring6webapp.controllers.MyController;

@SpringBootTest
class ApplicationTests {
	
    @Autowired
    ApplicationContext applicationContext;
	
    @Autowired
    MyController myController;
    
    @Test
    void testAutowireOfController() {
        System.out.println(myController.sayHello());
    }
    
    @Test
    void testGetControllerFromCtx() {
        MyController myController = applicationContext.getBean(MyController.class);

        System.out.println(myController.sayHello());
    }

	@Test
	void contextLoads() {
	}

}
