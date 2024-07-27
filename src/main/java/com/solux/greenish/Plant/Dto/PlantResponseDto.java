package com.solux.greenish.Plant.Dto;

import com.solux.greenish.Photo.Dto.PhotoResponseDto;
import lombok.Builder;

@Builder
public class PlantResponseDto {
    private Long plantId;
    private PhotoResponseDto photo;
}
