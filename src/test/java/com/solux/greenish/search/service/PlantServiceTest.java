package com.solux.greenish.search.service;


import com.solux.greenish.dto.PlantDetail;
import com.solux.greenish.dto.PlantResponse;
import com.solux.greenish.search.repository.ApiPlantRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;

class PlantServiceTest {
    @Value("${nongsaro.api.key}")
    private String apiKey;
    @Value("${nongsaro.api.url}")
    private String baseUrl;

    @Mock
    ApiPlantRepository apiPlantRepository;
    RestTemplate restTemplate=new RestTemplate();

    PlantService plantService=new PlantService(restTemplate,apiPlantRepository);
    @Test
    void name() throws Exception {
        //given
        String name="가울테리아";
        //when
        PlantResponse plantResponse = plantService.SearchPlantByName(name);
        //then
        List<PlantResponse.Body.Plant> plantList = plantResponse.getBody().getPlantList();
        for (PlantResponse.Body.Plant plant : plantList) {
            plant.getCntntsSj().contains(name);

        }


    }


}