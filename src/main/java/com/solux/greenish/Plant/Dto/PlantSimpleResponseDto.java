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
    private String photoUrl;

    public static PlantSimpleResponseDto of(Plant plant, String photoUrl) {
        return PlantSimpleResponseDto.builder()
                .plantId(plant.getId())
                .photoUrl(photoUrl)
                .build();
    }
}