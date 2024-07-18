package com.solux.greenish.Plant.Repository;

import com.solux.greenish.Plant.Domain.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findByUserId(Long userId);
    List<Plant> findByName(String name);
}
