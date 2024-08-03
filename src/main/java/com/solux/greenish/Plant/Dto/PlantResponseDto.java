package com.solux.greenish.Plant.Dto;

import com.solux.greenish.Photo.Dto.PhotoResponseDto;
import com.solux.greenish.Plant.Domain.Plant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PlantResponseDto {
    private Long plantId;
    private PhotoResponseDto photo;

    public PlantResponseDto toDto(Plant plant, PhotoResponseDto photo) {
        return PlantResponseDto.builder()
                .plantId(plant.getId())
                .photo(photo)
                .build();
    }
}
