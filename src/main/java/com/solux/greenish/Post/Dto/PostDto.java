package com.solux.greenish.Post.Dto;

import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.Post.Domain.Post;
import com.solux.greenish.User.Domain.User;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;


public class PostDto {

    private static ModelMapper modelMapper = new ModelMapper();


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static public class PostSimpleDto {
        private Long id;
        private String title;
        private LocalDate createdAt;
        private String content;
        private String photo_path;

        public static PostSimpleDto of(Post post) {
            return PostSimpleDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .createdAt(post.getCreatedAt())
                    .content(post.getContent())
                    .photo_path(post.getPhoto_path())
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static public class PostDetailDto {
        private Long id;
        private Long userId;
        private Long plantId;
        private String title;
        private String content;
        private LocalDate createdAt;
        private String photo_path;

        public static PostDetailDto of(Post post) {
            return modelMapper.map(post, PostDetailDto.class);
        }

        public PostDetailDto(Post post) {
            this.id = post.getId();
            this.userId = post.getUser().getId();
            this.plantId = post.getPlant().getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.createdAt = post.getCreatedAt();
            this.photo_path = post.getPhoto_path();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static public class PostCreateDto {
        private Long userId;
        private Long plantId;
        private String title;
        private String content;
        private LocalDate createdAt;
        private String photo_path;
        public Post toEntity(User user, Plant plant) {
            return Post.builder()
                    .user(user)
                    .plant(plant)
                    .title(title)
                    .content(content)
                    .createdAt(createdAt)
                    .photo_path(photo_path)
                    .build();
        }
    }

    // 정보 수정 Dto
    @Getter
    static public class PostModifyDto {
        private Long plantId;
        private String title;
        private String content;
        private String photo_path;

        @Builder
        public PostModifyDto(Long plantId, String title, String content, String photo_path) {
            this.plantId = plantId;
            this.title = title;
            this.content = content;
            this.photo_path = photo_path;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class IdResponse {
        private Long id;
        public static IdResponse of(Post post) {
            return new IdResponse(post.getId());
        }
    }

}
