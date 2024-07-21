package com.solux.greenish.search.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.solux.greenish.search.service.PlantService;

@Component
public class AppInitializer {

    private final PlantService plantService;

    public AppInitializer(PlantService plantService) {
        this.plantService = plantService;
    }

    @Bean
    public ApplicationRunner initializer() {
        return args -> {
            // DB에 테이블이 있는지 확인하고, 없으면 데이터 저장 로직 실행
            if (plantService.countPlants() == 0) {
                plantService.fetchAndSaveAllPlants();
            }
        };
    }
}
