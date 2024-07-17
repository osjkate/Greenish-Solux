package com.solux.greenish.Plant.Domain;

import com.solux.greenish.Calendar.Domain.Watering;
import com.solux.greenish.Post.Domain.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plant {
    @Id @Column(name = "plant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "plant")
    List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "plant")
    List<Watering> waterings = new ArrayList<>();

    private String name;

    private String code;
}
