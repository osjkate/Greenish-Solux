package com.solux.greenish.Plant.Dto;

import com.solux.greenish.Calendar.Dto.WateringResponseDto;
import com.solux.greenish.Photo.Dto.PhotoResponseDto;
import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.Post.Dto.PostSimpleResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class PlantDetailResponseDto {
    private Long id;
    private String name;
    private String code;
    private List<PostSimpleResponseDto> posts = new ArrayList<>();
    private List<WateringResponseDto> completeWaterings = new ArrayList<>();
    private WateringResponseDto watering;
    private PhotoResponseDto photo;

    public static PlantDetailResponseDto of(Plant plant, List<PostSimpleResponseDto> posts, PhotoResponseDto photo) {
        return PlantDetailResponseDto.builder()
                .id(plant.getId())
                .name(plant.getName())
                .posts(posts)
                .photo(photo)
//                .completeWaterings(completedWaterings)
//                .watering(scheduledWatering)
                .build();
    }
}