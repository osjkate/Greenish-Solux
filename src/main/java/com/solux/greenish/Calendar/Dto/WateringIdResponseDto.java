package com.solux.greenish.Calendar.Dto;

import com.solux.greenish.Calendar.Domain.Watering;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WateringIdResponseDto {
    private Long id;
    public static WateringIdResponseDto of(Watering watering) {
        return new WateringIdResponseDto(watering.getId());
    }
}