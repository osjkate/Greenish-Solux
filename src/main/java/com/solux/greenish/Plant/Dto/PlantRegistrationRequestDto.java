package com.solux.greenish.Plant.Dto;

import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.search.entity.ApiPlant;
import lombok.Getter;

@Getter
public class PlantRegistrationRequestDto {
    private Long userId;
    private String distbNm;
    private String name;
    private String age;
    private String photoPath;
    private boolean isAlarm;

    public Plant toEntity(User user, int wateringCycle, ApiPlant apiPlant) {
        return Plant.builder()
                .user(user)
                .name(name)
                .age(age)
                .photoPath(photoPath)
                .apiPlant(apiPlant)
                .isAlarm(isAlarm)
                .wateringCycle(wateringCycle)
                .build();
    }
}