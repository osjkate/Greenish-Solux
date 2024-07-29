package com.solux.greenish.Calendar.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WateringRequestDto{
    private Long userId;
    private LocalDate date;

}