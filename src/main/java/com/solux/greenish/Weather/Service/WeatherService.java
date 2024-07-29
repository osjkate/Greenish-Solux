package com.solux.greenish.Weather.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solux.greenish.Environment.Domain.Environment;
import com.solux.greenish.Environment.Repository.EnvironmentRepository;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.User.Repository.UserRepository;
import com.solux.greenish.Weather.Dto.WeatherDto;
import com.solux.greenish.Weather.Repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final UserRepository userRepository;
    private final EnvironmentRepository environmentRepository;
    private final WeatherRepository weatherRepository;
    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.forecast.url}")
    private String forecastUrl;

    @Value("${weather.current.url}")
    private String currentUrl;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional(readOnly = true)
    public List<WeatherDto> getWeatherForecast(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));

        Environment environment = user.getEnvironment();
        if (environment == null) {
            throw new RuntimeException("환경 정보를 찾을 수 없습니다.");
        }

        double latitude = environment.getLatitude();
        double longitude = environment.getLongitude();

        return getWeatherForecastFromAPI(latitude, longitude, environment);
    }

    @Transactional(readOnly = true)
    public WeatherDto getCurrentWeather(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));

        Environment environment = user.getEnvironment();
        if (environment == null) {
            throw new RuntimeException("환경 정보를 찾을 수 없습니다.");
        }

        double latitude = environment.getLatitude();
        double longitude = environment.getLongitude();

        return getCurrentWeatherFromAPI(latitude, longitude, environment);
    }

    private List<WeatherDto> getWeatherForecastFromAPI(double latitude, double longitude, Environment environment) {
        String url = String.format("%s?lat=%f&lon=%f&units=metric&appid=%s", forecastUrl, latitude, longitude, apiKey);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        return parseWeatherForecastData(response);
    }

    private WeatherDto getCurrentWeatherFromAPI(double latitude, double longitude, Environment environment) {
        String url = String.format("%s?lat=%f&lon=%f&units=metric&appid=%s", currentUrl, latitude, longitude, apiKey);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        return parseCurrentWeatherData(response);
    }

    private List<WeatherDto> parseWeatherForecastData(String response) {
        List<WeatherDto> weatherDataList = new ArrayList<>();

        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode list = root.path("list");

            for (int i = 0; i < list.size(); i += 8) { // 하루에 8개의 데이터 (3시간 간격)
                JsonNode weatherData = list.get(i);
                LocalDate date = LocalDate.parse(weatherData.path("dt_txt").asText().split(" ")[0]);
                double maxTemp = weatherData.path("main").path("temp_max").asDouble();
                double minTemp = weatherData.path("main").path("temp_min").asDouble();
                double humidity = weatherData.path("main").path("humidity").asDouble();
                String weatherCode = weatherData.path("weather").get(0).path("main").asText();

                WeatherDto weatherDto = new WeatherDto(date, maxTemp, minTemp, humidity, weatherCode);
                weatherDataList.add(weatherDto);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("날씨 데이터를 파싱하는 중 오류가 발생했습니다.");
        }

        return weatherDataList;
    }

    private WeatherDto parseCurrentWeatherData(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            LocalDate date = LocalDate.now(ZoneId.of("UTC"));  // 현재 날짜를 가져옴
            double temp = root.path("main").path("temp").asDouble();
            double humidity = root.path("main").path("humidity").asDouble();
            String weatherCode = root.path("weather").get(0).path("main").asText();

            return new WeatherDto(date, temp, temp, humidity, weatherCode);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("현재 날씨 데이터를 파싱하는 중 오류가 발생했습니다.");
        }
    }
}