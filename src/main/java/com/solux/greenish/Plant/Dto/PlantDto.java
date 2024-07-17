package com.solux.greenish.Plant.Dto;

import com.solux.greenish.Calendar.Domain.Watering;
import com.solux.greenish.Calendar.Dto.WateringDto.*;
import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.Post.Dto.PostDto.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


public class PlantDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static public class PlantDetailDto {
        private Long id;
        private String name;
        private String code;
        private List<PostSimpleDto> posts = new ArrayList<>();
        private List<WateringDetailDto> completeWaterings = new ArrayList<>();
        private WateringDetailDto watering;

        public static PlantDetailDto of(Plant plant, List<PostSimpleDto> posts, List<WateringDetailDto> completedWaterings, WateringDetailDto scheduledWatering){
            plant.getPosts().stream().map(PostSimpleDto::of);
            return PlantDetailDto.builder()
                    .id(plant.getId())
                    .name(plant.getName())
                    .posts(posts)
                    .completeWaterings(completedWaterings)
                    .watering(scheduledWatering)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static public class PlantSimpleDto {
        private Long id;
        private String name;
        private String code;

        public static PlantSimpleDto of(Plant plant) {
            return PlantSimpleDto.builder()
                    .id(plant.getId())
                    .name(plant.getName())
                    .code(plant.getCode())
                    .build();
        }
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static public class PlantRegistDto {
        private String name;
        private String code;
        private Long wateringId;

        public Plant toEntity() {
            return Plant.builder()
                    .name(name).build();
        }
    }
}
