package com.everis.bc.infoManagerService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@EnableEurekaClient
@SpringBootApplication
public class InfoManagerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfoManagerServiceApplication.class, args);
	}

}
