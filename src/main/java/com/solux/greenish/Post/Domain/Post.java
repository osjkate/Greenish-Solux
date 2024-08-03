package com.solux.greenish.Post.Domain;

import com.solux.greenish.Photo.Domain.Photo;
import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.User.Domain.User;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToOne
    @JoinColumn(name = "photo_id")
    private Photo photo;

    // post 수정 함수
    public void update(String title,
                       String content) {
        this.title = title;
        this.content = content;
    }
    public void updatePhoto(Photo photo) {
        this.photo = photo;
    }

}
