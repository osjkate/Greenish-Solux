package com.solux.greenish.User.Domain;

import com.solux.greenish.Environment.Domain.Environment;
import com.solux.greenish.Photo.Domain.Photo;
import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.Post.Domain.Post;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "user")
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "environment_id")
    private Environment environment;

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @OneToOne
    @JoinColumn(name = "photo_id")
    private Photo photo;

    // 알림 기능 추가 예정

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();


    public void updatePhoto(Photo photo) {
        this.photo = photo;
    }
}

