package com.everis.bc.servicioGestionInformacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@EnableEurekaClient
@SpringBootApplication
public class ServicioGestionInformacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioGestionInformacionApplication.class, args);
	}

}
