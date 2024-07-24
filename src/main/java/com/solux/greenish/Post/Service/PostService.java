package com.solux.greenish.Post.Service;

import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.Post.Domain.Post;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.Post.Dto.PostDto.*;
import com.solux.greenish.Plant.Repository.PlantRepository;
import com.solux.greenish.Post.Repository.PostRepository;
import com.solux.greenish.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PlantRepository plantRepository;

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 사용자를 찾을 수 없습니다. ID: " + userId));
    }

    private Plant findPlantById(Long plantId) {
        return plantRepository.findById(plantId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 식물을 찾을 수 없습니다. ID: " + plantId));
    }

    // 조회
    // post id로 게시물 상세조회
    @Transactional(readOnly = true)
    public PostDetailDto getPostDetailById(Long id) {
        return postRepository.findById(id)
                .map(PostDetailDto::new)
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재하지 않습니다. "));
    }

    // user의 id로 게시물 전체 조회
    @Transactional(readOnly = true)
    public List<PostSimpleDto> getAllPostByUserId(Long userId) {
        return postRepository.findAllByUserId(userId).stream()
                .map(PostSimpleDto::of).collect(Collectors.toList());
    }

    // plant id 로 게시물 전체 조회
    @Transactional(readOnly = true)
    public List<PostSimpleDto> getAllPostByPlantId(Long plantId) {
        return postRepository.findAllByPlantId(plantId).stream()
                .map(PostSimpleDto::of).collect(Collectors.toList());
    }

    // 게시물 전체 조회
    @Transactional(readOnly = true)
    public List<PostSimpleDto> getAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostSimpleDto::of)
                .collect(Collectors.toList());
    }

    // 게시물 등록
    @Transactional
    public IdResponse postCreate(PostCreateDto postDto) {
        User user = findUserById(postDto.getUserId());
        Plant plant = findPlantById(postDto.getPlantId());
        Post post = postDto.toEntity(user, plant);
        postRepository.save(post);
        return IdResponse.of(post);
    }

    // 게시물 수정
    @Transactional
    public IdResponse postModify(Long id, PostModifyDto request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다. "));
        Plant plant = plantRepository.findById(request.getPlantId()).get();
        post.update(plant, request.getTitle(), request.getContent(), request.getPhoto_path());
        return IdResponse.of(post);
    }


    // 게시물 삭제
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다. "));
        postRepository.delete(post);
    }

}
