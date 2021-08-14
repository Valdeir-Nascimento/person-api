package br.com.nascimento.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@Configuration
public class WebConfig {
	
	public void adicionarCorsMapping(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
	}
	
	

	
}
