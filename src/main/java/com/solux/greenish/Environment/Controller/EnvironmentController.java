package com.solux.greenish.Environment.Controller;

import com.solux.greenish.Environment.Domain.Environment;
import com.solux.greenish.Environment.Service.EnvironmentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/environment")
public class EnvironmentController {

    private final EnvironmentService environmentService;

    // 위치 업데이트
    @PutMapping("/update-location")
    public ResponseEntity<Void> updateLocation(@RequestHeader(name = "Authorization") String token, @RequestBody Environment environment) {
        environmentService.updateLocation(token, environment);
        return ResponseEntity.ok().build();
    }

    // 위치 요청 DTO
    @Getter
    @Setter
    public static class LocationRequest {
        private double latitude;
        private double longitude;
    }
}
