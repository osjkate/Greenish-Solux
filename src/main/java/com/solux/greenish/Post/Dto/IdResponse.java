package com.solux.greenish.Post.Dto;

import com.solux.greenish.Post.Domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IdResponse {
    private Long id;
    public static IdResponse of(Post post) {
        return new IdResponse(post.getId());
    }
}
