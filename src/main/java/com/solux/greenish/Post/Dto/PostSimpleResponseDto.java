package com.solux.greenish.Post.Dto;

import com.solux.greenish.Photo.Dto.PhotoResponseDto;
import com.solux.greenish.Post.Domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PostSimpleResponseDto {
    private Long postId;
    private String title;
    private LocalDate createdAt;
    private String content;
    private String photoUrl;

    public static PostSimpleResponseDto of(Post post, String photoUrl) {
        return PostSimpleResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .createdAt(post.getCreatedAt())
                .content(post.getContent())
                .photoUrl(photoUrl)
                .build();
    }
}
