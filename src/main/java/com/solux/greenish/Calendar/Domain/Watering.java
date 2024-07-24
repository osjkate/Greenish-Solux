package com.solux.greenish.Calendar.Domain;

import com.solux.greenish.Plant.Domain.Plant;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity @Getter
public class Watering {
    @Id
    @Column(name = "watering_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate date;
}
