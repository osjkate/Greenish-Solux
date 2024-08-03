package com.solux.greenish.Calendar.Domain;

import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.User.Domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

// 물주기 일정
@Entity @Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Watering {
    @Id
    @Column(name = "watering_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // TODO : 상태 삭제 -> complete null 값인지 확인
    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate scheduleDate;

    @Setter
    private LocalDate completeDate;

    private int wateringCycle;

    @Builder.Default
    private int overdays = 0;

    public void updateWateringDate(int cycle) {
        scheduleDate = scheduleDate.minusDays(wateringCycle).plusDays(cycle);
        wateringCycle = cycle;
    }

    public void postponeWateringDate() {
        scheduleDate = scheduleDate.plusDays(1);
        overdays += 1;
    }


    public void updateStatus() {
        if (status == Status.PRE) {
            status = Status.COMPLETED;
            completeDate = scheduleDate;
        } else {
            status = Status.PRE;
            completeDate = null;
        }
    }
}
