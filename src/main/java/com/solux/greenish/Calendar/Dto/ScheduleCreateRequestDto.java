package com.solux.greenish.Calendar.Dto;

import com.solux.greenish.Calendar.Domain.Schedule;

import java.time.LocalDate;

public class ScheduleCreateRequestDto {
    private String content;
    private LocalDate date;

    public Schedule schedule(ScheduleCreateRequestDto request) {
        return Schedule.builder()
                .date(date)
                .content(content)
                .build();
    }
}
