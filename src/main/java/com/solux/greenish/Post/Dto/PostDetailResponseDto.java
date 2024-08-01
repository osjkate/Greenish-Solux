package com.solux.greenish.Post.Dto;

import com.solux.greenish.Photo.Dto.PhotoResponseDto;
import com.solux.greenish.Post.Domain.Post;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailResponseDto {
    private Long postId;
    private String plantName;
    private String title;
    private String content;
    private LocalDate createdAt;
    private PhotoResponseDto photo;

    public static PostDetailResponseDto of(Post post, PhotoResponseDto photo) {
        return PostDetailResponseDto.builder()
                .postId(post.getId())
                .plantName(post.getPlant().getName())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .photo(photo)
                .build();
    }
}
