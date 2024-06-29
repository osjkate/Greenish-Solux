package com.solux.greenish.Domain;

import jakarta.persistence.*;

@Entity
public class Environment {

    @Id @Column(name = "environment_id")
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "environment")
    private User user;

    private String location;

    private double ave_temp;

    private double ave_humid;

    private String field;
}
