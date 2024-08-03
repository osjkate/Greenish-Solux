package com.solux.greenish.User.Domain;

import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.Post.Domain.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String location;
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Plant> plants = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String password;

    @OneToOne
    @JoinColumn(name = "environment_id")
    private Environment environment;

    // 알림 기능 추가 예정

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

}
