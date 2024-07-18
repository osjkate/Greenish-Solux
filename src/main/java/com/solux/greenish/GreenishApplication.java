package com.solux.greenish;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan(basePackages = "com.solux.greenish")
public class GreenishApplication {

	public static void main(String[] args) {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		loggerContext.getLogger("org.springframework.security.oauth2").setLevel(Level.DEBUG);
		loggerContext.getLogger("org.springframework.web.client.RestTemplate").setLevel(Level.DEBUG);
		loggerContext.getLogger("com.solux").setLevel(Level.DEBUG);
		SpringApplication.run(GreenishApplication.class, args);
	}

}
