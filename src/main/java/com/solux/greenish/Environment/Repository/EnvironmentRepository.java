package com.solux.greenish.Environment.Repository;

import com.solux.greenish.Environment.Domain.Environment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnvironmentRepository extends JpaRepository<Environment, Long> {
}
