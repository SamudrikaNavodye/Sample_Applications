package com.auth0.sso_with_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@RestController
@EnableWebSecurity
public class SsoWithSpringBootApplication {

	@GetMapping("/")
	public String welcome2User(Principal principal) {
		return "Hello "+ principal.getName() + " Welcome to Auth0 Sample.";
	}

	public static void main(String[] args) {
		SpringApplication.run(SsoWithSpringBootApplication.class, args);
	}

}
