package com.solux.greenish.Dto;

import com.solux.greenish.Domain.Plant;
import com.solux.greenish.Domain.Post;
import com.solux.greenish.Domain.User;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Builder
public class PostDto {

    private static ModelMapper modelMapper = new ModelMapper();


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class PostSimpleDto {
        private Long id;
        private String title;
        private LocalDate date;
        private String content;
        private String photo_path;

        public static PostSimpleDto of(Post post) {
            return modelMapper.map(post, PostSimpleDto.class);
        }
        public PostSimpleDto(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.date = post.getDate();
            this.photo_path = post.getPhoto_path();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class PostDetailDto {
        private Long id;
        private Long userId;
        private Long plantId;
        private String title;
        private String content;
        private LocalDate date;
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
            this.date = post.getDate();
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
        private LocalDate date;
        private String photo_path;
        public Post toEntity(User user, Plant plant) {
            return Post.builder()
                    .user(user)
                    .plant(plant)
                    .title(title)
                    .content(content)
                    .date(date)
                    .photo_path(photo_path)
                    .build();
        }
    }



}
