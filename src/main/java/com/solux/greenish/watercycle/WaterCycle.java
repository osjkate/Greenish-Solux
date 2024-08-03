package com.solux.greenish.watercycle;

import com.solux.greenish.search.entity.ApiPlant;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class WaterCycle {
    @Id
    @Column(name = "water_cycle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cycle;
}
