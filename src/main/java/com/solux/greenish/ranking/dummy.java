/*
package com.solux.greenish.ranking;

import com.solux.greenish.Photo.Domain.Photo;
import com.solux.greenish.Photo.Repository.PhotoRepository;
import com.solux.greenish.User.Domain.RoleType;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.User.Repository.UserRepository;
import com.solux.greenish.Post.Domain.Post;
import com.solux.greenish.Post.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class dummy {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PhotoRepository photoRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void init() {
        // 필요한 경우 주석을 제거하여 초기화할 때 더미 데이터를 생성합니다.
        deleteDummyUsersAndPosts();
        createDummyUsersAndPosts();
    }

    // 더미 데이터 생성 메서드
    @Transactional
    public void createDummyUsersAndPosts() {
        List<User> users = new ArrayList<>();
        List<Photo> photos = new ArrayList<>();

        for (int i = 1; i <= 40; i++) {
            // 프로필 사진 생성
            Photo photo = Photo.builder()
                    .fileName("profile" + i + ".jpg")
                    .photoPath("path/to/profile" + i + ".jpg")
                    .build();
            photos.add(photo);

            // 사용자 생성
            User user = User.builder()
                    .role(RoleType.USER)
                    .email("user" + i + "@example.com")
                    .nickname("User" + i)
                    .password(passwordEncoder.encode("password!")) // 비밀번호 암호화
                    .photo(photo) // 프로필 사진 연결
                    .build();
            users.add(user);
        }

        photoRepository.saveAll(photos);
        userRepository.saveAll(users);

        List<Post> posts = new ArrayList<>();
        for (User user : users) {
            int postCount = (int) (Math.random() * 100) + 1; // 1~100개의 포스트 생성
            for (int j = 0; j < postCount; j++) {
                Post post = Post.builder()
                        .user(user)
                        .title("Post " + j)
                        .content("Content of post " + j)
                        .createdAt(LocalDate.now().minusDays((int) (Math.random() * 7))) // 지난 7일간의 임의의 날짜
                        .build();
                posts.add(post);
            }
        }
        postRepository.saveAll(posts);
    }

    // 더미 데이터 삭제 메서드
    @Transactional
    public void deleteDummyUsersAndPosts() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        photoRepository.deleteAll();
    }
}
*/
