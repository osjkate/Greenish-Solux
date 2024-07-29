package com.solux.greenish.Environment.Domain;

import com.solux.greenish.User.Domain.User;
import com.solux.greenish.Weather.Domain.Weather;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity @Getter
@NoArgsConstructor
public class Environment {

    @Id @Column(name = "environment_id")
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "environment")
    private User user;

    private String location;

    private double ave_temp;

    private double ave_humid;

    private String field;

    private double latitude;  // 위도
    private double longitude; // 경도

    @OneToMany(mappedBy = "environment")
    private List<Weather> weatherList;

    public void updateLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
