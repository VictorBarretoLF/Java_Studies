package com.springfeignintro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringCloudOpenFeignIntroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudOpenFeignIntroApplication.class, args);
	}

}
