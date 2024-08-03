package com.solux.greenish.Calendar.Dto;

import com.solux.greenish.Calendar.Domain.Schedule;
import com.solux.greenish.Calendar.Domain.Watering;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleIdResponseDto {
    private Long id;
    public static ScheduleIdResponseDto of(Schedule schedule) {
        return new ScheduleIdResponseDto(schedule.getId());
    }
}
