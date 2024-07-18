package com.solux.greenish.User.Repository;

import com.solux.greenish.User.Domain.Environment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnvironmentRepository extends JpaRepository<Environment, Long> {
    Optional<Environment> findByUserId(Long userId);
}
