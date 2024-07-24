package com.solux.greenish.search.config;
import com.solux.greenish.search.contoller.PlantSearchController;
import com.solux.greenish.search.repository.ApiPlantRepository;
import com.solux.greenish.search.service.PlantService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@ComponentScan(basePackages = "com.solux.greenish.search")
@Configuration
public class Appconfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
