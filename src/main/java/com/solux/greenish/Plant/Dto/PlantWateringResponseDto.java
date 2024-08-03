package com.solux.greenish.Plant.Dto;

import com.solux.greenish.Calendar.Dto.WateringResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
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