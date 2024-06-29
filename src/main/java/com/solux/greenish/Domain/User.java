package com.solux.greenish.Domain;

import jakarta.persistence.*;

@Entity
public class User {

    @Id @Column(name = "user_id")
    @GeneratedValue
    private Long id;

    private String nickname;

    @OneToOne
    private Environment environment;
}
