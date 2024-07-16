package com.solux.greenish.Controller;

import com.solux.greenish.Dto.PostDto.*;
import com.solux.greenish.Dto.PostDto.PostDetailDto;
import com.solux.greenish.Dto.Response.BasicResponse;
import com.solux.greenish.Dto.Response.DataResponse;
import com.solux.greenish.Service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    // 조회
    // post id로 게시물 상세조회
    @GetMapping("/{post_id}")
    public ResponseEntity<? extends BasicResponse> getPostById(
            @PathVariable("post_id") Long id) {
        PostDetailDto postDetail = postService.getPostDetailById(id);
        return ResponseEntity.ok(new DataResponse<>(postDetail));

    }

    // user id로 게시물 전체 조회
    @GetMapping("/user/{user_id}")
    public ResponseEntity<? extends BasicResponse> getAllPostByUserID(
            @PathVariable("user_id") Long user_id) {
        List<PostSimpleDto> posts = postService.getAllPostByUserId(user_id);
        return ResponseEntity.ok(new DataResponse<>(posts));
    }

    // plant id로 게시물 전체 조회
    @GetMapping("/plant/{plant_id}")
    public ResponseEntity<? extends BasicResponse> getAllPostByPlantID(
            @PathVariable("plant_id") Long plant_id) {
        List<PostSimpleDto> posts = postService.getAllPostByPlantId(plant_id);
        return ResponseEntity.ok(new DataResponse<>(posts));
    }

    // 게시물 전체 조회
    @GetMapping("/all")
    public ResponseEntity<? extends BasicResponse> getAllPost() {
        return ResponseEntity.ok(new DataResponse<>(postService.getAllPost()));
    }

    // 게시물 등록
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> postCreate(
            @Valid @RequestBody PostCreateDto request
    ) {
        return ResponseEntity.ok().body(
                new DataResponse<IdResponse>(postService.postCreate(request))
        );

    }

    // 게시물 수정
    @PatchMapping("/{post_id}")
    public ResponseEntity<? extends BasicResponse> modifyPost(
            @PathVariable("post_id") Long post_id,
            @RequestBody PostModifyDto request) {
        postService.postModify(post_id, request);
        return ResponseEntity.ok().build();
    }

    // 게시물 삭제
    @DeleteMapping("/{post_id}")
    public ResponseEntity<? extends BasicResponse> deletePost(
            @PathVariable("post_id") Long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
