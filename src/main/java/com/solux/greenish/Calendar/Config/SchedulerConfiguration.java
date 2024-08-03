package com.solux.greenish.Calendar.Config;

import com.solux.greenish.Calendar.Service.WateringScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@RequiredArgsConstructor
public class SchedulerConfiguration {
    private final WateringScheduleService wateringScheduleService;


//    @Scheduled(cron = "0 0/5 * * * ?")
    @Scheduled(cron = "0 0 0 * * ?")
    public void run() {
        wateringScheduleService.WateringPostpone();
    }

}
