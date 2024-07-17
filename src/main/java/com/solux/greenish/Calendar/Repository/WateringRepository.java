package com.solux.greenish.Calendar.Repository;

import com.solux.greenish.Calendar.Domain.Watering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WateringRepository extends JpaRepository<Watering, Long> {
    List<Watering> findAllByPlantId(Long plantId);
}
