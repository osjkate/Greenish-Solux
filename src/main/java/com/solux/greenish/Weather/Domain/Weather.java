package com.solux.greenish.Weather.Domain;

import com.solux.greenish.Environment.Domain.Environment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity @Getter
@NoArgsConstructor
public class Weather {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private double maxTemp;
    private double minTemp;
    private double humidity;
    private String weatherCode;

    @ManyToOne
    @JoinColumn(name = "environment_id")
    private Environment environment;

    public Weather(LocalDate date, double maxTemp, double minTemp, double humidity, String weatherCode, Environment environment) {
        this.date = date;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.humidity = humidity;
        this.weatherCode = weatherCode;
        this.environment = environment;
    }
}
