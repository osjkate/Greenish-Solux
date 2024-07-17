package com.solux.greenish.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor
public class Plant {
    @Id @Column(name = "plant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "plant")
    List<Post> posts = new ArrayList<>();

    private String plantname;

    private String code;
}
