package com.solux.greenish.Alarm.Controller;

import com.solux.greenish.Alarm.Domain.Alarm;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alarm")
public class AlarmController {

    private Alarm alarm;

    // 기본 생성자
    public AlarmController() {
        // 초기 알림 설정
        this.alarm = new Alarm();
    }

    // 알림 설정 업데이트
    @PutMapping("/update")
    public Alarm updateAlarm(@RequestBody Alarm newAlarm, @RequestHeader("Authorization") String token) {
        this.alarm.setAll(newAlarm.isAll());
        this.alarm.setHapticMode(newAlarm.isHapticMode());
        this.alarm.setPreview(newAlarm.isPreview());
        this.alarm.setAllPlantWatering(newAlarm.isAllPlantWatering());
        return this.alarm;
    }

    // 현재 알림 설정 조회
    @GetMapping
    public Alarm getAlarm(@RequestHeader("Authorization") String token) {
        return this.alarm;
    }
}
