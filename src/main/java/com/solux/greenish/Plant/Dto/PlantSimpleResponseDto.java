package com.solux.greenish.Plant.Dto;

import com.solux.greenish.Photo.Dto.PhotoResponseDto;
import com.solux.greenish.Plant.Domain.Plant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlantSimpleResponseDto {
    private Long plantId;
    private PhotoResponseDto photo;

    public static PlantSimpleResponseDto of(Plant plant, PhotoResponseDto photo) {
        return PlantSimpleResponseDto.builder()
                .plantId(plant.getId())
                .photo(photo)
                .build();
    }
}