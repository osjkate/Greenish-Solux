package com.solux.greenish.Calendar.Dto;

import com.solux.greenish.Calendar.Domain.Schedule;
import com.solux.greenish.User.Domain.User;

import java.time.LocalDate;

public class ScheduleCreateRequestDto {
    private String content;
    private LocalDate date;

    public Schedule toSchedule(User user) {
        return Schedule.builder()
                .date(date)
                .content(content)
                .user(user)
                .build();
    }
}
