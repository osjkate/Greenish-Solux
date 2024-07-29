package com.solux.greenish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication()
public class GreenishApplication {

	public static void main(String[] args) {

		SpringApplication.run(GreenishApplication.class, args);
	}

}
