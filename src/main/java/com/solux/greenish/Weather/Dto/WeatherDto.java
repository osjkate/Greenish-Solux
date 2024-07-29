package com.solux.greenish.Weather.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class WeatherDto {
    private LocalDate date;
    private double maxTemp;
    private double minTemp;
    private double humidity;
    private String weatherCode;
}
