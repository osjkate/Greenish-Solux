package com.solux.greenish.Post.Dto;

import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.Post.Domain.Post;
import com.solux.greenish.User.Domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCreateRequestDto {
    private Long plantId;
    private String title;
    private String content;
    private String filename;

    public Post toEntity(User user, Plant plant) {
        return Post.builder()
                .user(user)
                .plant(plant)
                .title(title)
                .content(content)
                .createdAt(LocalDate.now())
                .build();
    }
}
