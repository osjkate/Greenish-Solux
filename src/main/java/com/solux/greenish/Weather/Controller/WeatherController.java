package com.solux.greenish.Weather.Controller;

import com.solux.greenish.Weather.Dto.WeatherDto;
import com.solux.greenish.Weather.Service.WeatherService;
import com.solux.greenish.login.Jwt.JwtUtil;
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
    private final JwtUtil jwtUtil;


    // 특정 사용자의 5일간의 날씨 데이터를 조회
    @GetMapping("/forecast")
    public ResponseEntity<List<WeatherDto>> getWeatherForecast(@RequestHeader(name = "Authorization") String token) {
        String email = jwtUtil.getEmail(token.split(" ")[1]);
        List<WeatherDto> forecast = weatherService.getWeatherForecast(email);
        return ResponseEntity.ok(forecast);
    }

    // 특정 위치의 현재 날씨를 조회
    // 특정 위치의 현재 날씨를 조회
    @GetMapping("/current")
    public ResponseEntity<WeatherDto> getCurrentWeather(@RequestHeader(name = "Authorization") String token) {
        String email = jwtUtil.getEmail(token.split(" ")[1]);
        WeatherDto currentWeather = weatherService.getCurrentWeather(email);
        return ResponseEntity.ok(currentWeather);
    }
}