package com.solux.greenish.Calendar.Dto;

import com.solux.greenish.Calendar.Domain.Schedule;
import com.solux.greenish.Calendar.Domain.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long scheduleId;
    private String content;
    private Status status;
    private LocalDate date;


    public static ScheduleResponseDto of(Schedule schedule) {
        return ScheduleResponseDto.builder()
                .scheduleId(schedule.getId())
                .content(schedule.getContent())
                .date(schedule.getDate())
                .build();
    }
}
