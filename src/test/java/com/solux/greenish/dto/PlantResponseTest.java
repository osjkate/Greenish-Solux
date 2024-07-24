package com.solux.greenish.dto;

import com.solux.greenish.search.dto.PlantResponse;
import org.junit.jupiter.api.Test;

class PlantResponseTest {
    @Test
    void testSetRtnFileUrl() {
        PlantResponse.Body.Plant plant = new PlantResponse.Body.Plant();
        plant.setRtnFileUrl("djald.jpg|lffjdklsfa.jpg");
        plant.oneRtnFile();
        System.out.println(plant.getRtnFileUrl());
    }}