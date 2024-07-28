package com.solux.greenish.Post.Dto;

import com.solux.greenish.Photo.Dto.PhotoResponseDto;
import com.solux.greenish.Post.Domain.Post;
import lombok.Builder;

@Builder
public class PostResponseDto {
    private Long postId;
    private PhotoResponseDto photo;

    public static PostResponseDto toDto(Post post, PhotoResponseDto photo) {
        return PostResponseDto.builder()
                .postId(post.getId())
                .photo(photo)
                .build();
    }

}
