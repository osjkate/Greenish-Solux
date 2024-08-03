package com.solux.greenish.ranking.Dto;

import com.solux.greenish.Calendar.Domain.Watering;
import com.solux.greenish.Calendar.Dto.WateringResponseDto;
import com.solux.greenish.ranking.Domain.Ranking;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RankingDto {
    private Long userId;
    private String nickname;
    private int rank;
    private String profileImageUrl;


    public static RankingDto buildDto(Ranking ranking) {
            return RankingDto.builder()
                    .userId(ranking.getUser().getId())
                    .nickname(ranking.getUser().getNickname())
                    .rank(ranking.getRank())
                    .build();
        }

}
