package com.everis.bc.infoManagerService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {
	
	@Value("${valores.uri_to_gateway}")
	private String url;
	
	@Bean
	public WebClient webClientRegister() {
		return WebClient.create(url);
	}
}
