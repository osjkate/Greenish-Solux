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
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class dummy {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PhotoRepository photoRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int NAME_LENGTH = 8; // 이름 길이


    // 랜덤한 이름 생성 메서드
    private String generateRandomName() {
        Random random = new SecureRandom();
        StringBuilder builder = new StringBuilder(NAME_LENGTH);
        for (int i = 0; i < NAME_LENGTH; i++) {
            int index = random.nextInt(ALPHABET.length());
            builder.append(ALPHABET.charAt(index));
        }
        return builder.toString();
    }

    // 더미 데이터 생성 메서드
    @Transactional
    public void createDummyUsersAndPosts() {
        List<User> users = new ArrayList<>();
        List<Photo> photos = new ArrayList<>();

        for (int i = 1; i <= 40; i++) {
            // 랜덤 이름 생성
            String randomName = generateRandomName();

            // 프로필 사진 생성
            Photo photo = Photo.builder()
                    .fileName("profile" + i + ".jpg")
                    .photoPath("path/to/profile" + i + ".jpg")
                    .build();
            photos.add(photo);

            // 사용자 생성
            User user = User.builder()
                    .role(RoleType.USER)
                    .email("user"+i+"@example.com")
                    .nickname(randomName)
                    .password(passwordEncoder.encode("password!")) // 비밀번호 암호화
                    .photo(photo) // 프로필 사진 연결
                    .build();
            users.add(user);
        }

        photoRepository.saveAll(photos);
        userRepository.saveAll(users);

        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            int postCount = i + 10; // 각 사용자마다 고유한 포스트 수

            for (int j = 0; j < postCount; j++) {
                Post post = Post.builder()
                        .user(user)
                        .title("Post " + (j + 1))
                        .content("Content of post " + (j + 1))
                        .createdAt(LocalDate.now().minusDays((int) (Math.random() * 7))) // 지난 7일간의 임의의 날짜
                        .build();
                posts.add(post);
            }
        }
        postRepository.saveAll(posts);
    }
    //쓰나...?
    // 데이터 삭제 메서드
    @Transactional
    public void deleteDummyUsersAndPosts() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        photoRepository.deleteAll();
    }
}

