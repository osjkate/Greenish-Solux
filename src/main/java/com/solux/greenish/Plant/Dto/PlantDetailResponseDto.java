package com.solux.greenish.Plant.Dto;

import com.solux.greenish.Calendar.Dto.WateringResponseDto;
import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.Post.Dto.PostDto.*;
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
    private List<PostSimpleDto> posts = new ArrayList<>();
    private List<WateringResponseDto> completeWaterings = new ArrayList<>();
    private WateringResponseDto watering;

    public static PlantDetailResponseDto of(Plant plant, List<PostSimpleDto> posts) {
        plant.getPosts().stream().map(PostSimpleDto::of);
        return PlantDetailResponseDto.builder()
                .id(plant.getId())
                .name(plant.getName())
                .posts(posts)
//                .completeWaterings(completedWaterings)
//                .watering(scheduledWatering)
                .build();
    }
}