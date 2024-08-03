package com.solux.greenish.ranking.Dto;



import com.solux.greenish.Photo.Domain.Photo;
import com.solux.greenish.User.Domain.User;
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
    private int recordCount;

    public static RankingDto buildDto(Ranking ranking) {
        User user = ranking.getUser();
        Photo photo = user.getPhoto();
        String profileImageUrl = photo != null ? photo.getPhotoPath() : null;

        return RankingDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .rank(ranking.getUserRank())
                .profileImageUrl(profileImageUrl)
                .recordCount(ranking.getRecordCount())
                .build();
    }

}
