package com.solux.greenish.ranking.Domain;

import com.solux.greenish.User.Domain.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor

public class Ranking {

    @Id
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user; //아이디
    private int recordCount;
    private int rank;


    public Ranking(User user, int recordCount, int i) {
    }
}
