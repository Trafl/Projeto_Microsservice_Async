package com.empiricus.service_usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServiceUsuarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceUsuarioApplication.class, args);
	}

}
