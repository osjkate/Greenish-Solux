package com.solux.greenish.search.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.solux.greenish.search.service.PlantService;

import javax.xml.bind.JAXBException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class AppInitializer {

    private final PlantService plantService;
    private static final Logger logger = Logger.getLogger(AppInitializer.class.getName());

    public AppInitializer(PlantService plantService) {
        this.plantService = plantService;
    }

    @Bean
    public ApplicationRunner initializer() {
        return args -> {
            try {
                // DB에 테이블이 있는지 확인하고, 없으면 데이터 저장 로직 실행
                if (plantService.countPlants() == 0) {
                    plantService.fetchAndSaveAllPlants();
                }
            } catch (JAXBException e) {
                logger.log(Level.SEVERE, "Fail to save Plants from api:", e);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "An unexpected error occurred while initializing the application.", e);
            }
        };
    }
}
