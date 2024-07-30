package com.solux.greenish.Calendar.Repository;

import com.solux.greenish.Calendar.Domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByUserId(Long userId);

    List<Schedule> findByUserIdAndDate(Long userId, LocalDate date);


}
