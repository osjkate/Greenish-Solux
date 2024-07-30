package com.solux.greenish.Plant.Controller;

import com.solux.greenish.Plant.Dto.PlantCreateRequestDto;
import com.solux.greenish.Plant.Dto.PlantModifyRequestDto;
import com.solux.greenish.Plant.Service.PlantService;
import com.solux.greenish.Response.BasicResponse;
import com.solux.greenish.Response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plants")
public class PlantController {

    private final PlantService plantService;

    // id로 식물 조회
    @GetMapping("/{plant_id}")
    public ResponseEntity<? extends BasicResponse> getPlantById(
            @PathVariable("plant_id") Long plantId) {
        return ResponseEntity.ok(new DataResponse<>(plantService.getPlantById(plantId)));
    }

    // 전체 식물 조회
    @GetMapping("/all")
    public ResponseEntity<? extends BasicResponse> getAllPlant() {
        return ResponseEntity.ok(new DataResponse<>(plantService.getAllPlant()));
    }

    // user_id 로 전체 식물 조회
    @GetMapping("/user/{user_id}")
    public ResponseEntity<? extends BasicResponse> getAllPlantByUserId(
            @PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(new DataResponse<>(plantService.getAllPlantByUserId(userId)));
    }

    // 식물의 watering 모두 조회
    @GetMapping("/waterings/{plant_id}")
    public ResponseEntity<? extends BasicResponse> getAllWatering(
            @PathVariable("plant_id") Long plantId) {
        return ResponseEntity.ok(new DataResponse<>(plantService.getWatering(plantId)));
    }

    // 이름으로 조회
    @GetMapping("/name/{name}")
    public ResponseEntity<? extends BasicResponse> getPlantByName(
            @PathVariable("name") String name) {
        return ResponseEntity.ok(new DataResponse<>(plantService.getPlantByName(name)));
    }

    // 식물 생성
    @PostMapping
    public ResponseEntity<? extends BasicResponse> createPlant(
            @RequestBody PlantCreateRequestDto request) {
        return ResponseEntity.ok(new DataResponse<>(plantService.createPlant(request)));
    }


    // 식물 update
    @PutMapping
    public ResponseEntity<? extends BasicResponse> updatePlant(
            @RequestBody PlantModifyRequestDto request) {
        return ResponseEntity.ok(new DataResponse<>(plantService.updatePlant(request)));
    }

    // 식물 삭제
    @DeleteMapping("/{plant_id}")
    public ResponseEntity<? extends BasicResponse> deletePlant(
            @PathVariable("plant_id") Long plantId) {
        plantService.deletePlant(plantId);
        return ResponseEntity.noContent().build();
    }
}
