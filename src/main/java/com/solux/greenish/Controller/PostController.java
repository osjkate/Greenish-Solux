package com.solux.greenish.Controller;

import com.solux.greenish.Domain.Post;
import com.solux.greenish.Dto.PostDto.PostDetailDto;
import com.solux.greenish.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    // 조회
    // post id로 게시물 상세조회
    @GetMapping("/{id}")
    public ResponseEntity<PostDetailDto> getPostById(@PathVariable Long id) {
        try {
            PostDetailDto postDetail = postService.getPostDetailById(id);
            return ResponseEntity.ok(postDetail);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // user id로 게시물 전체 조회

    // plant id로 게시물 전체 조회

    // 게시물 등록

    // 게시물 수정

    // 게시물 삭제

}
