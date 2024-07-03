package com.solux.greenish.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    private String title;

    private String content;

    private LocalDate date;

    private String photo_path;

    // post 생성 함수
    public static Post createPost(User user, Plant plant, String title,
                                  String content, LocalDate date, String photo_path){
        return Post.builder()
                .user(user)
                .plant(plant)
                .title(title)
                .content(content)
                .date(date)
                .photo_path(photo_path)
                .build();
    }

    // post 수정 함수
    public void update(User user, Plant plant, String title,
                       String content, LocalDate date, String photo_path) {
        this.user = user;
        this.plant = plant;
        this.title = title;
        this.content = content;
        this.date = date;
        this.photo_path = photo_path;
    }

}
