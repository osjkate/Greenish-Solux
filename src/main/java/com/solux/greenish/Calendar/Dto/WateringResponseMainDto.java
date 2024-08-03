package com.solux.greenish.Calendar.Dto;

import com.solux.greenish.Calendar.Domain.Status;
import com.solux.greenish.Calendar.Domain.Watering;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class WateringResponseMainDto {
    private Long wateringId;
    private String plantName;
    private String imageUrl;
    private Status status;
    private LocalDate wateringDay;

    public static WateringResponseMainDto toDto(Watering watering, String imageUrl) {
        return WateringResponseMainDto.builder()
                .wateringId(watering.getId())
                .plantName(watering.getPlant().getName())
                .imageUrl(imageUrl)
                .status(watering.getStatus())
                .wateringDay(watering.getScheduleDate())
                .build();
    }
}
