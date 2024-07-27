package com.solux.greenish.Plant.Dto;

import com.solux.greenish.Calendar.Dto.WateringResponseDto;
import lombok.Builder;

import java.util.List;

@Builder
public class PlantWateringResponseDto {
    private List<WateringResponseDto> completedWaterings;
    private WateringResponseDto scheduledWatering;

    public static PlantWateringResponseDto of(List<WateringResponseDto> completedWaterings, WateringResponseDto scheduledWatering) {
        return PlantWateringResponseDto.builder()
                .completedWaterings(completedWaterings)
                .scheduledWatering(scheduledWatering)
                .build();
    }

}