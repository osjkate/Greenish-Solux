package com.solux.greenish.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

class PlantResponseTest {
    @Test
    void testSetRtnFileUrl() {
        PlantResponse.Body.Plant plant = new PlantResponse.Body.Plant();
        plant.setRtnFileUrl("djald.jpg|lffjdklsfa.jpg");
        plant.oneRtnFile();
        System.out.println(plant.getRtnFileUrl());
    }}