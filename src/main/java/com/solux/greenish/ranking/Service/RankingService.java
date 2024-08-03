package com.solux.greenish.ranking.Service;
import com.solux.greenish.Post.Repository.PostRepository;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.User.Repository.UserRepository;
import com.solux.greenish.login.Jwt.JwtUtil;
import com.solux.greenish.ranking.Domain.Ranking;
import com.solux.greenish.ranking.Dto.RankingDto;
import com.solux.greenish.ranking.Repository.RankingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
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

    //유저 조회
    private User getUserByToken(String token) {
        return userRepository.findByEmail(jwtUtil.getEmail(token.split(" ")[1]))
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 조회할 수 없습니다."));
    }
    //먼저 전체적으로 순위를 정리해둬야함..왜냐면 내가 있든 없든 앞에 사람들 순위가 필요하기 때문에..그냥 계산해두는 것이 맞는 것 같다......아마.
    //매주 월요일에 랭킹이 초기화 된다
    @Scheduled(cron = "0 0 0 * * MON")
    @Transactional
    public void updateRanking(){
        //랭킹 계산하기
        //주간 랭킹이므로 7일
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
        //user 모두를 계산해야 낮은 순위 유저에게도 랭킹을 줄 수 있음
        List<User> users = userRepository.findAll();
        List<Ranking> rankings = users.stream()
                .map(user -> {
                    //user의 post 개수 세기
                    int recordCount = postRepository.countByUserIdAndCreatedAtAfter(user, oneWeekAgo);
                    //Entity 생성하기
                    return new Ranking(user,recordCount,0);
                })
                .sorted((r1, r2) -> Integer.compare(r2.getRecordCount(), r1.getRecordCount())) //정렬
                .collect(Collectors.toList());
        //게시글 수에 따른 랭킹 부여...
        for (int i = 0; i < rankings.size(); i++) {
            rankings.get(i).setRank(i + 1);
        }

        // 3. 기존 랭킹 삭제 및 새로운 랭킹 저장
        rankingRepository.deleteAll();
        rankingRepository.saveAll(rankings);

    }

    //랭킹은 매주 갱신되고
    //랭킹 확인시 20위 +내 랭킹을 전달해야함.
    public Map<String, Object> getUserRanking(String token){
        User user = getUserByToken(token);
        //유저 랭킹 가지고 옴,
        Ranking userRank = rankingRepository.findByUserId(user.getId());
        RankingDto userRankDto = RankingDto.buildDto(userRank);
        Pageable topTwenty = PageRequest.of(0, 20);
        List<RankingDto> topUsers= rankingRepository.findAllByOrderByRankAsc(topTwenty).stream()
                .map(RankingDto::buildDto)
                .toList();
         Map<String,Object> response=new HashMap<>();
         response.put("MyRank",userRankDto);
         response.put("TopUsers",topUsers);
        return response;


    }



}
