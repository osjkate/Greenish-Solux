package com.solux.greenish.Calendar.Controller;

import com.solux.greenish.Calendar.Dto.ScheduleCreateRequestDto;
import com.solux.greenish.Calendar.Dto.ScheduleUpdateRequestDto;
import com.solux.greenish.Calendar.Service.ScheduleService;
import com.solux.greenish.Response.BasicResponse;
import com.solux.greenish.Response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    // Date로 Schedule 찾기
    @GetMapping("/date/{date}")
    public ResponseEntity<? extends BasicResponse> getAllScheduleByDate(
            @RequestHeader("Authorization") String token,
            @PathVariable("date") LocalDate date) {
        return ResponseEntity.ok(new DataResponse<>(scheduleService.getScheduleByDate(token, date)));
    }

    // Schedule 생성
    @PostMapping
    public ResponseEntity<? extends BasicResponse> createSchedule(
            @RequestHeader("Authorization") String token,
            @RequestBody ScheduleCreateRequestDto request) {
        return ResponseEntity.ok(new DataResponse<>(scheduleService.createSchedule(token, request)));
    }

    // Schedule 모두 조회
    @GetMapping
    public ResponseEntity<? extends BasicResponse> getAllSchedule(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(new DataResponse<>(scheduleService.getAllSchedule(token)));
    }

//    @PutMapping
//    public ResponseEntity<? extends BasicResponse> updateSchedule(
//            @RequestBody ScheduleUpdateRequestDto request) {
//
//    }
}
