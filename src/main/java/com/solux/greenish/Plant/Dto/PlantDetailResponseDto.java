package com.solux.greenish.Plant.Dto;

import com.solux.greenish.Calendar.Dto.WateringResponseDto;
import com.solux.greenish.Photo.Dto.PhotoResponseDto;
import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.Post.Dto.PostSimpleResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlantDetailResponseDto {
    private Long id;
    private String name;
    private String code;
    private int watercycle;
    private List<PostSimpleResponseDto> posts = new ArrayList<>();
    private PhotoResponseDto photo;

    public static PlantDetailResponseDto of(Plant plant, List<PostSimpleResponseDto> posts, PhotoResponseDto photo) {
        return PlantDetailResponseDto.builder()
                .id(plant.getId())
                .name(plant.getName())
                .watercycle(plant.getWateringCycle())
                .posts(posts)
                .photo(photo)
                .build();
    }
}