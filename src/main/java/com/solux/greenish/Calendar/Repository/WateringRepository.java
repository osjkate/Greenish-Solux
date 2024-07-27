package com.solux.greenish.Calendar.Repository;

import com.solux.greenish.Calendar.Domain.Status;
import com.solux.greenish.Calendar.Domain.Watering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WateringRepository extends JpaRepository<Watering, Long> {
    List<Watering> findByPlantId(Long plantId);

    List<Watering> findByUserId(Long userId);

    List<Watering> findByUserIdAndStatus(Long userId, Status status);

    List<Watering> findByPlantIdAndStatus(Long plantId, Status status);

    Optional<Watering> findFirstByPlantIdAndStatus(Long plantId, Status status);

    boolean existsByPlantIdAndStatus(Long plantId, Status status);

    List<Watering> findByDateAndUserId(LocalDate date, Long userId);

}
