package com.solux.greenish.Service;

import com.solux.greenish.Domain.Plant;
import com.solux.greenish.Domain.Post;
import com.solux.greenish.Domain.User;
import com.solux.greenish.Dto.PostDto.*;
import com.solux.greenish.Repository.PlantRepository;
import com.solux.greenish.Repository.PostRepository;
import com.solux.greenish.Repository.UserRepository;
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
                .map(PostDetailDto::of)
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재하지 않습니다. "));
    }

    // user의 id로 게시물 전체 조회
    @Transactional(readOnly = true)
    public List<PostSimpleDto> getAllPostDetailByUserId(Long userId) {
        return postRepository.findAllByUserId(userId).stream()
                .map(PostSimpleDto::new).collect(Collectors.toList());
    }

    // plant id 로 게시물 전체 조회
    @Transactional(readOnly = true)
    public List<PostSimpleDto> getAllPostDetailByPlantId(Long plantId) {
        return postRepository.findAllByPlantId(plantId).stream()
                .map(PostSimpleDto::new).collect(Collectors.toList());
    }

    // 게시물 등록
    @Transactional
    public PostDetailDto postCreatePost(PostCreateDto postDto) {

        User user = findUserById(postDto.getUserId());
        Plant plant = findPlantById(postDto.getPlantId());
        Post post = postDto.toEntity(user, plant);
        postRepository.save(post);
        return PostDetailDto.of(post);
    }

    // 게시물 수정

    // 게시물 삭제
}
