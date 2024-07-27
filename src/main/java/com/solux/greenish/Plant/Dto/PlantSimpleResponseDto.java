package com.solux.greenish.Plant.Dto;

import com.solux.greenish.Plant.Domain.Plant;
import lombok.Builder;

@Builder
public class PlantSimpleResponseDto {
    private Long plantId;
    private String fileName;

    public static PlantSimpleResponseDto of(Plant plant) {
        return PlantSimpleResponseDto.builder()
                .plantId(plant.getId())
                .fileName(plant.getPhoto().getFileName())
                .build();
    }
}