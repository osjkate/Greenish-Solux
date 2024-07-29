package com.solux.greenish.Weather.Repository;

import com.solux.greenish.Environment.Domain.Environment;
import com.solux.greenish.Weather.Domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    List<Weather> findByEnvironmentAndDateBetween(Environment environment, LocalDate startDate, LocalDate endDate);
    List<Weather> findByEnvironment(Environment environment);
}