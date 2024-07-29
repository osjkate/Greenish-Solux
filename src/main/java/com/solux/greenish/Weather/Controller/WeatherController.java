package com.solux.greenish.Weather.Controller;

import com.solux.greenish.Weather.Dto.WeatherDto;
import com.solux.greenish.Weather.Service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;


    // 특정 사용자의 5일간의 날씨 데이터를 조회
    @GetMapping("/forecast")
    public ResponseEntity<List<WeatherDto>> getWeatherForecast(@RequestParam (name = "userId") Long userId) {
        List<WeatherDto> forecast = weatherService.getWeatherForecast(userId);
        return ResponseEntity.ok(forecast);
    }

    // 특정 위치의 현재 날씨를 조회
    @GetMapping("/current")
    public ResponseEntity<WeatherDto> getCurrentWeather(@RequestParam (name = "userId") Long userId) {
        WeatherDto currentWeather = weatherService.getCurrentWeather(userId);
        return ResponseEntity.ok(currentWeather);
    }
}