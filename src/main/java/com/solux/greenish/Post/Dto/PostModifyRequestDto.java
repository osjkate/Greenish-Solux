package com.solux.greenish.Post.Dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostModifyRequestDto {
    private Long postId;
    private Long plantId;
    private String title;
    private String content;
    private String fileName;
}
