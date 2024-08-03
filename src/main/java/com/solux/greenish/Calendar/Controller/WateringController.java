package com.solux.greenish.Calendar.Controller;

import com.solux.greenish.Calendar.Dto.WateringRequestDto;
import com.solux.greenish.Calendar.Service.WateringService;
import com.solux.greenish.Response.BasicResponse;
import com.solux.greenish.Response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/waterings")
public class WateringController {

    private final WateringService wateringService;

    // plant_id로 watering 모두 조회
    @GetMapping("/plant/{plant_id}")
    public ResponseEntity<? extends BasicResponse> getAllWateringByPlantId(
            @PathVariable("plant_id") Long plantId) {
        return ResponseEntity.ok(new DataResponse<>(wateringService.getAllWateringByPlantId(plantId)));
    }


    // plant_id로 완료하지 않은 watering 조회
    @GetMapping("/plant/{plant_id}/pre")
    public ResponseEntity<? extends BasicResponse> getScheduledWateringByPlantId(
            @PathVariable("plant_id") Long plantId) {
        return ResponseEntity.ok(new DataResponse<>(wateringService.getScheduledWateringByPlantId(plantId)));
    }

    // user_id로 watering 모두 조회
    @GetMapping("/user")
    public ResponseEntity<? extends BasicResponse> getAllWateringByUserId(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(new DataResponse<>(wateringService.getAllWateringByUserId(token)));
    }

    // user_id로 완료하지 않은 watering 모두 조회
    @GetMapping("/user/pre")
    public ResponseEntity<? extends BasicResponse> getAllWateringByUserIdAndStatus(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(new DataResponse<>(wateringService.getAllWateringByUserIdAndStatus(token)));
    }

    // Date로 watering 조회
    @GetMapping("/date/{date}")
    public ResponseEntity<? extends BasicResponse> getAllWateringByDate(
            @RequestHeader("Authorization") String token,
            @PathVariable("date") LocalDate date) {
        return ResponseEntity.ok(new DataResponse<>(wateringService.getAllWateringByDate(token, date)));
    }


    // 물주기 완료
    @PutMapping("/complete/{watering_id}")
    public ResponseEntity<? extends BasicResponse> completeWatering(
            @PathVariable("watering_id") Long wateringId) {
        return ResponseEntity.ok(new DataResponse<>(wateringService.completeWatering(wateringId)));
    }

    // 물주기 미완료
    @PutMapping("/postpone/{watering_id}")
    public ResponseEntity<? extends BasicResponse> postponeWatering(
            @PathVariable("watering_id") Long wateringId) {
        return ResponseEntity.ok(new DataResponse<>(wateringService.postponeWatering(wateringId)));
    }

    @GetMapping("/main")
    public ResponseEntity<? extends BasicResponse> getWateringForMain(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(new DataResponse<>(wateringService.getAllWateringSinceToday(token)));
    }
}
