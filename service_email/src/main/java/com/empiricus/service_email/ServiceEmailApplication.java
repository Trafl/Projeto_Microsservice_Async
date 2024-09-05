package com.empiricus.service_email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ServiceEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceEmailApplication.class, args);
	}

}
