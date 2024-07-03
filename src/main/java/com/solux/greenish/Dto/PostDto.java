package com.solux.greenish.Dto;

import com.solux.greenish.Domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

public class PostDto {

    private static ModelMapper modelMapper = new ModelMapper();

    @Getter @Setter
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



}
