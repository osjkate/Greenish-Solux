package com.solux.greenish.ranking.controller;

import com.solux.greenish.ranking.Service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ranking")
public class RankingController {
    private final RankingService rankingService;

    @Autowired
    public RankingController(RankingService rankingService){
        this.rankingService=rankingService;
    }
    //내 순위포함한 랭킹 전달!
    @GetMapping
    public ResponseEntity<?> MyRank(@RequestHeader("Authorization") String token){
        Map<String, Object> userRanking = rankingService.getUserRanking(token);
        return new ResponseEntity(userRanking, HttpStatus.OK);
    }
}
