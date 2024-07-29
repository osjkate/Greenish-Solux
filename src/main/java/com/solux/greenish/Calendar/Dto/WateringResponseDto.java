package com.solux.greenish.Calendar.Dto;

import com.solux.greenish.Calendar.Domain.Status;
import com.solux.greenish.Calendar.Domain.Watering;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WateringResponseDto {
    private Long wateringId;
    private Long plantId;
    private Status status;
    private LocalDate scheduleDate;
    private LocalDate completeDate;
    private int overdays;

    public static WateringResponseDto of(Watering watering) {
        return WateringResponseDto.builder()
                .wateringId(watering.getId())
                .plantId(watering.getPlant().getId())
                .status(watering.getStatus())
                .scheduleDate(watering.getScheduleDate())
                .completeDate(watering.getCompleteDate())
                .overdays(watering.getOverdays())
                .build();
    }
}
