package com.solux.greenish.ranking.Service;

import com.solux.greenish.Post.Repository.PostRepository;
import com.solux.greenish.User.Domain.RoleType;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.User.Repository.UserRepository;
import com.solux.greenish.login.Jwt.JwtUtil;
import com.solux.greenish.ranking.Domain.Ranking;
import com.solux.greenish.ranking.Dto.RankingDto;
import com.solux.greenish.ranking.Repository.RankingRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final RankingRepository rankingRepository;


    // 매주 월요일에 랭킹 초기화
    @Scheduled(cron = "0 0 0 * * MON")
    @Transactional
    public void updateRanking() {
        LocalDate oneWeekAgo = LocalDate.now().minusDays(7);
        List<User> users = userRepository.findAll();
        System.out.println("****************users**********여기까진 되는디"+users);
        List<Ranking> rankings = users.stream()
                .map(user -> {
                    User managedUser = userRepository.findById(user.getId()).orElseThrow();
                    int recordCount = postRepository.countByUserIdAndCreatedAtAfter(managedUser.getId(), oneWeekAgo);
                    System.out.println("여기도 되나?????*********************************");
                    return new Ranking(managedUser, recordCount, 0);
                })
                .sorted((r1, r2) -> Integer.compare(r2.getRecordCount(), r1.getRecordCount()))
                .collect(Collectors.toList());
        System.out.println("???????????여기가 안 되나?????*********************************");

        int currentRank = 1;
        for (int i = 0; i < rankings.size(); i++) {
            Ranking ranking = rankings.get(i);
            // 현재 순위와 이전 순위의 기록 수가 다르면 순위 업데이트
            if (i == 0 || rankings.get(i).getRecordCount() != rankings.get(i - 1).getRecordCount()) {
                currentRank = i + 1;
            }
            ranking.setUserRank(currentRank);
        }
        rankingRepository.deleteAll();
        rankingRepository.saveAll(rankings);
        System.out.println("8888888888888888888???????????save problem입니다*********************************");

    }


    private User getUserByToken(String token) {
        return userRepository.findByEmail(jwtUtil.getEmail(token.split(" ")[1]))
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 조회할 수 없습니다."));
    }

 //랭킹
    public Map<String, Object> getUserRanking(String token) {
        User user = getUserByToken(token);
        Ranking userRank = rankingRepository.findByUserId(user.getId());
        RankingDto userRankDto = RankingDto.buildDto(userRank);

        Pageable topTwenty = PageRequest.of(0, 20);
        List<RankingDto> topUsers = rankingRepository.findAllByOrderByUserRankAsc(topTwenty).stream()
                .map(RankingDto::buildDto)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("MyRank", userRankDto);
        response.put("TopUsers", topUsers);
        return response;
    }
    //강제업뎃
    public int UpdateByAdmin(String token){
        String msg;
        User user = getUserByToken(token);
        if (user.getRole() == RoleType.ADMIN) {
            updateRanking();
            return 1;
        } else {
            return 2;
        }

    }
}
