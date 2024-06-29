package com.solux.greenish.Repository;

import com.solux.greenish.Domain.Environment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnvironmentRepository extends JpaRepository<Environment, Long> {
    Optional<Environment> findByUserId(Long userId);
}
