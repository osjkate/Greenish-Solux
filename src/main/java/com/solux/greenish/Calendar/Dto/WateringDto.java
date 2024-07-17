package com.solux.greenish.Calendar.Dto;

import com.solux.greenish.Calendar.Domain.Status;
import com.solux.greenish.Calendar.Domain.Watering;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class WateringDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class WateringDetailDto {
        private Long id;
        private Status status;
        private LocalDate date;

        public static WateringDetailDto of(Watering watering) {
            return WateringDetailDto.builder()
                    .id(watering.getId())
                    .status(watering.getStatus())
                    .date(watering.getDate())
                    .build();
        }
    }
}
