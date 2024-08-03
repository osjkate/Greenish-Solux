package com.solux.greenish.ranking.Repository;

import com.solux.greenish.ranking.Domain.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking,Long> {
    Ranking findByUserId(Long id) ;
    List<Ranking> findAllByOrderByUserRankAsc(Pageable topTwenty);
}
