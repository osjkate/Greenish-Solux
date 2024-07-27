package com.solux.greenish.Calendar.Domain;

import com.solux.greenish.User.Domain.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

// 일정 (해야할 일)
@Entity
@Getter
public class Schedule {
    @Id @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
