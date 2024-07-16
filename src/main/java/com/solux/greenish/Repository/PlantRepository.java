package com.solux.greenish.Repository;

import com.solux.greenish.Domain.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
}
