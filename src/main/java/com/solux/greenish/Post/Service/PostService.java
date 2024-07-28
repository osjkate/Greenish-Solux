package com.solux.greenish.Post.Service;

import com.solux.greenish.Photo.Domain.Photo;
import com.solux.greenish.Photo.Dto.PhotoResponseDto;
import com.solux.greenish.Photo.Repository.PhotoRepository;
import com.solux.greenish.Photo.Service.PhotoService;
import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.Post.Domain.Post;
import com.solux.greenish.Post.Dto.*;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.Plant.Repository.PlantRepository;
import com.solux.greenish.Post.Repository.PostRepository;
import com.solux.greenish.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PlantRepository plantRepository;
    private final PhotoService photoService;
    private final PhotoRepository photoRepository;

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 사용자를 찾을 수 없습니다. ID: " + userId));
    }

    private Plant findPlantById(Long plantId) {
        return plantRepository.findById(plantId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 식물을 찾을 수 없습니다. ID: " + plantId));
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 포스트를 찾을 수 없습니다. "));
    }
    private Photo findPhotoById(Long photoId) {
        return photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사진을 조회할 수 없습니다."));
    }

    // 조회
    // post id로 게시물 상세조회
    @Transactional(readOnly = true)
    public PostDetailResponseDto getPostDetailById(Long id) {
        Post post = findPostById(id);
        PhotoResponseDto photo = photoService.getFilePath(post.getPhoto().getId());

        return PostDetailResponseDto.of(post, photo);
    }

    // user의 id로 게시물 전체 조회
    @Transactional(readOnly = true)
    public List<PostSimpleResponseDto> getAllPostByUserId(Long userId) {
        List<Post> posts = postRepository.findAllByUserId(userId);
        return posts.stream().map((post) -> PostSimpleResponseDto.of(post, photoService.getFilePath(post.getPhoto().getId()))).toList();
    }

    // plant id 로 게시물 전체 조회
    @Transactional(readOnly = true)
    public List<PostSimpleResponseDto> getAllPostByPlantId(Long plantId) {
        List<Post> posts = postRepository.findAllByPlantId(plantId);
        return posts.stream().map((post) -> PostSimpleResponseDto.of(post, photoService.getFilePath(post.getPhoto().getId()))).toList();
    }

    // 게시물 전체 조회
    @Transactional(readOnly = true)
    public List<PostSimpleResponseDto> getAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map((post) -> PostSimpleResponseDto.of(post, photoService.getFilePath(post.getPhoto().getId()))).toList();
    }

    // 게시물 등록
    @Transactional
    public PostResponseDto postCreate(PostCreateRequestDto request) {
        User user = findUserById(request.getUserId());
        Plant plant = findPlantById(request.getPlantId());
        Post post = request.toEntity(user, plant);
        postRepository.save(post);

        PhotoResponseDto photo = null;
        if (request.getFilename() != null) {
            photo = photoService.generatePreSignedDto(post.getId(), request.getFilename());
            post.updatePhoto(findPhotoById(photo.getPhotoId()));
        }

        return PostResponseDto.toDto(post, photo);
    }

    // 게시물 수정
    @Transactional
    public PostResponseDto postModify(PostModifyRequestDto request) {
        Post post = findPostById(request.getPostId());
        Plant plant = findPlantById(request.getPlantId());
        post.update(plant, request.getTitle(), request.getContent());
        String newFileName = request.getFileName();
        String currentFileName = post.getPhoto().getFileName();

        PhotoResponseDto photo = null;
        if (newFileName != null && newFileName.equals(currentFileName)) {
            photo = photoService.generatePreSignedDto(post.getId(), request.getFileName());
            post.updatePhoto(findPhotoById(photo.getPhotoId()));
        }
        return PostResponseDto.toDto(post, photo);
    }


    // 게시물 삭제
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다. "));
        if (post.getPhoto() != null) {
            photoService.deletePhoto(post.getPhoto().getId());
        }
        postRepository.delete(post);
    }

}
