package com.solux.greenish.Calendar.Dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WateringRequestDto{
    private Long UserId;
    private LocalDate date;

}