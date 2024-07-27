package com.solux.greenish.Calendar.Dto;

import com.solux.greenish.Calendar.Domain.Watering;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WateringIdResponse{
    private Long id;
    public static WateringIdResponse of(Watering watering) {
        return new WateringIdResponse(watering.getId());
    }
}