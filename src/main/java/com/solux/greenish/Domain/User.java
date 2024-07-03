package com.solux.greenish.Domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id @Column(name = "user_id")
    @GeneratedValue
    private Long id;

    private String nickname;

    private String location;

    // 환경 필요한가요..?
    @OneToOne
    private Environment environment;

    // 알림 기능 추가 예정

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

}
