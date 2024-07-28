package com.solux.greenish.Plant.Dto;

import com.solux.greenish.Photo.Dto.PhotoResponseDto;
import com.solux.greenish.Plant.Domain.Plant;
import lombok.Builder;

@Builder
public class PlantResponseDto {
    private Long plantId;
    private PhotoResponseDto photo;

    public static PlantResponseDto toDto(Plant plant, PhotoResponseDto photo) {
        return PlantResponseDto.builder()
                .plantId(plant.getId())
                .photo(photo)
                .build();
    }
}
