package com.solux.greenish.Plant.Dto;

import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.search.entity.ApiPlant;
import lombok.Getter;

@Getter
public class PlantCreateRequestDto {
    private Long userId;
    private String distbNm;
    private String name;
    private String age;
    private String fileName;
    private boolean isAlarm;

    public Plant toEntity(User user, int wateringCycle, ApiPlant apiPlant) {
        return Plant.builder()
                .user(user)
                .name(name)
                .age(age)
                .apiPlant(apiPlant)
                .isAlarm(isAlarm)
                .wateringCycle(wateringCycle)
                .build();
    }
}