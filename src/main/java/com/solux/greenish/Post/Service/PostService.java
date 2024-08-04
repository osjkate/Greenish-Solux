package com.solux.greenish.Post.Service;

import com.solux.greenish.Photo.Domain.Photo;
import com.solux.greenish.Photo.Dto.PhotoResponseDto;
import com.solux.greenish.Photo.Dto.PresignedUrlDto;
import com.solux.greenish.Photo.Repository.PhotoRepository;
import com.solux.greenish.Photo.Service.PhotoService;
import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.Post.Domain.Post;
import com.solux.greenish.Post.Dto.*;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.Plant.Repository.PlantRepository;
import com.solux.greenish.Post.Repository.PostRepository;
import com.solux.greenish.User.Repository.UserRepository;
import com.solux.greenish.login.Jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    private final PlantRepository plantRepository;
    private final PhotoService photoService;
    private final PhotoRepository photoRepository;

    private User findUserByToken(String token) {
        return userRepository.findByEmail(jwtUtil.getEmail(token.split(" ")[1]))
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 조회할 수 없습니다."));
    }

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
        String photo = null;
        if (post.getPhoto() == null) {
            photo = photoService.getCDNUrl("post/" + post.getId(), post.getPhoto().getPhotoPath());
        }
        return PostDetailResponseDto.of(post, photo);
    }

    // user의 id로 게시물 전체 조회
    @Transactional(readOnly = true)
    public List<PostSimpleResponseDto> getAllPostByUserId(String token) {
        List<Post> posts = postRepository.findAllByUserId(
                findUserByToken(token).getId());

        List<PostSimpleResponseDto> response = new ArrayList<>();
        for (Post post : posts) {
            String image = null;
            if (post.getPhoto() != null) {
                image = photoService.getCDNUrl("post/" + post.getId(), post.getPhoto().getPhotoPath());

            }
            response.add(PostSimpleResponseDto.of(post, image));

        }
        return response;
    }

    // plant id 로 게시물 전체 조회
    @Transactional(readOnly = true)
    public List<PostSimpleResponseDto> getAllPostByPlantId(Long plantId) {
        List<Post> posts = postRepository.findAllByPlantId(plantId);
        List<PostSimpleResponseDto> response = new ArrayList<>();
        for (Post post : posts) {
            String image = null;
            if (post.getPhoto() != null) {
                image = photoService.getCDNUrl("post/" + post.getId(), post.getPhoto().getPhotoPath());
            }
            response.add(PostSimpleResponseDto.of(post, image));

        }
        return response;
    }

    // 게시물 전체 조회
    @Transactional(readOnly = true)
    public List<PostSimpleResponseDto> getAllPost() {
        List<Post> posts = postRepository.findAll();
        List<PostSimpleResponseDto> response = new ArrayList<>();
        for (Post post : posts) {
            String image = null;
            if (post.getPhoto() != null) {
                image = photoService.getCDNUrl("post/" + post.getId(), post.getPhoto().getPhotoPath());

            }
            response.add(PostSimpleResponseDto.of(post, image));

        }
        return response;
    }

    // 게시물 등록
    @Transactional
    public PostResponseDto postCreate(String token, PostCreateRequestDto request) {
        User user = findUserByToken(token);
        Plant plant = findPlantById(request.getPlantId());
        Post post = request.toEntity(user, plant);
        postRepository.save(post);

        PhotoResponseDto photo = null;
        if (request.getFilename() != null) {
            photo = photoService.createPhoto(PresignedUrlDto.builder()
                    .prefix("post/" + post.getId())
                    .fileName(request.getFilename()).build());
            post.updatePhoto(findPhotoById(photo.getPhotoId()));
        }

        return PostResponseDto.toDto(post, photo);
    }

    // 게시물 수정
    @Transactional
    public PostResponseDto postModify(PostModifyRequestDto request) {
        Post post = findPostById(request.getPostId());

        post.update(request.getTitle(), request.getContent());
        String newFileName = request.getFileName();
        String currentFileName = post.getPhoto().getFileName();

        PhotoResponseDto photo = null;
        if (newFileName != null && !newFileName.equals(currentFileName)) {
            photoService.deletePhoto(post.getPhoto());
            photo = photoService.createPhoto(PresignedUrlDto.builder()
                    .prefix("post/" + post.getId())
                    .fileName(request.getFileName()).build());
            post.updatePhoto(findPhotoById(photo.getPhotoId()));
        }

        return PostResponseDto.toDto(post, photo);
    }


    // 게시물 삭제
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다. "));
        photoService.deletePhoto(post.getPhoto());
        postRepository.delete(post);
    }

}
