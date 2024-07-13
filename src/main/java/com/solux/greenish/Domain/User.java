package com.solux.greenish.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor
public class User {

    @Id @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String location;

    @OneToOne
    @JoinColumn(name = "environment_id")
    private Environment environment;

    // 알림 기능 추가 예정

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

}
