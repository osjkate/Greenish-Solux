package com.solux.greenish.Domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Post {
    @Id @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String title;

    private String content;

    private LocalDate date;

    private String photo_path;
}
