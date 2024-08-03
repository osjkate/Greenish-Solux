package com.solux.greenish.ranking.Dto;



import com.solux.greenish.ranking.Domain.Ranking;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RankingDto {
    private Long userId;
    private String nickname;
    private int rank;
    //프로필이 어디에 있는거지.....?
/*
    private String profileImageUrl;
*/
    private int recordCount;

    public static RankingDto buildDto(Ranking ranking) {
            return RankingDto.builder()
                    .userId(ranking.getUser().getId())
                    .nickname(ranking.getUser().getNickname())
                    .rank(ranking.getUserRank())
/*
                    .profileImageUrl(ranking.getUser().getProfileImageUrl())
*/
                    .recordCount(ranking.getRecordCount())
                    .build();
        }

}
