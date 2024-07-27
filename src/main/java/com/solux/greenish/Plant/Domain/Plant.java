package com.solux.greenish.Plant.Domain;

import com.solux.greenish.Calendar.Domain.Watering;
import com.solux.greenish.Post.Domain.Post;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.search.entity.ApiPlant;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Watering> waterings = new ArrayList<>();

    private String name;

    private String age;

    private String photoPath;

    @ManyToOne
    @JoinColumn(name = "api_plant_id")
    private ApiPlant apiPlant;

    private int wateringCycle;

    private boolean isAlarm;

    public void update(String name, String age, boolean isAlarm,
                       int wateringCycle, ApiPlant apiPlant, String photoPath
    ) {
        this.name = name;
        this.age = age;
        this.wateringCycle = wateringCycle;
        this.isAlarm = isAlarm;
        this.apiPlant = apiPlant;
        this.photoPath = photoPath;
    }
}
