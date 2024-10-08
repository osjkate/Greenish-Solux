package com.solux.greenish.Plant.Repository;

import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.User.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findByUserId(Long userId);

    Optional<Plant> findByName(String name);

    boolean existsByNameAndUser(String name, User user);
}
