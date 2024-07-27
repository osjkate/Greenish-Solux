package com.solux.greenish.Post.Domain;

import com.solux.greenish.Photo.Domain.Photo;
import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.User.Domain.User;
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

    private LocalDate createdAt;

    private String photo_path;

    @OneToOne
    @JoinColumn(name = "photo_id")
    private Photo photo;

    // post 수정 함수
    public void update( Plant plant, String title,
                       String content, String photo_path) {
        this.plant = plant;
        this.title = title;
        this.content = content;
        this.photo_path = photo_path;
    }

}
