package com.solux.greenish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class GreenishApplication {

	public static void main(String[] args) {

		SpringApplication.run(GreenishApplication.class, args);
	}

}
