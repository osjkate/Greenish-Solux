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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private int recordCount;
    private int userRank;

    public Ranking(User user, int recordCount, int userRank) {
        this.user = user;
        this.recordCount = recordCount;
        this.userRank = userRank;
    }
}
