package com.solux.greenish.ranking.controller;

import com.solux.greenish.ranking.Service.RankingService;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ranking")
public class RankingController {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final RankingService rankingService;

    @Autowired
    public RankingController(RankingService rankingService){
        this.rankingService=rankingService;
    }
    //내 순위포함한 랭킹 전달!
    @GetMapping
    public ResponseEntity<Map<String,Object>> MyRank(  @RequestHeader("Authorization") String token){
        System.out.println("token은 잘 온건가요.............????****************"+token);
        Map<String, Object> userRanking = rankingService.getUserRanking(token);
        return new ResponseEntity<>(userRanking, HttpStatus.OK);
    }
    //랭킹 강제 업뎃
    @GetMapping("/update")
    public ResponseEntity<String> updateRanking(@RequestHeader("Authorization") String token) {
        int result=rankingService.UpdateByAdmin(token);
        if (result==1){
            return  new ResponseEntity<>("성공",HttpStatus.OK);
        }
        else{
            return  new ResponseEntity<>("관리자만 가능",HttpStatus.FORBIDDEN);
        }
    }

}
