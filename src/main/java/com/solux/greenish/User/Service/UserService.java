package com.solux.greenish.User.Service;

import com.solux.greenish.Photo.Domain.Photo;
import com.solux.greenish.Photo.Dto.PhotoResponseDto;
import com.solux.greenish.Photo.Dto.PresignedUrlDto;
import com.solux.greenish.Photo.Repository.PhotoRepository;
import com.solux.greenish.Photo.Service.PhotoService;
import com.solux.greenish.User.Dto.UserDto.*;
import com.solux.greenish.User.Dto.UserIdResponse;
import com.solux.greenish.User.Repository.UserRepository;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.login.Jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PhotoRepository photoRepository;
    private final PhotoService photoService;

    private final JwtUtil jwtUtil;

    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    private Photo getPhoto(Long photoId) {
        return photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사진을 조회할 수 없습니다. "));
    }

    private User getUserByToken(String token) {
        return userRepository.findByEmail(jwtUtil.getEmail(token.split(" ")[1]))
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 조회할 수 없습니다."));
    }

    public boolean isNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    // 회원 가입
    @Transactional
    public UserIdResponse signUp(UserRegistDto request) {
        if (isEmailDuplicate(request.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 이메일입니다. ");
        }
        if (isNicknameDuplicate(request.getNickname())) {
            throw new RuntimeException("이미 사용 중인 닉네임입니다.");
        }

        User user = request.toUser(bCryptPasswordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        String url = null;
        if (request.getFileName() != null) {

            PhotoResponseDto photo = photoService.createPhoto(
                    PresignedUrlDto.builder()
                            .prefix("user/" + user.getId())
                            .fileName(request.getFileName()).build());
            user.updatePhoto(getPhoto(photo.getPhotoId()));
            url = photo.getUrl();
        }

        return UserIdResponse.builder()
                .userId(user.getId())
                .imageUrl(url).build();
    }

    // 삭제
    @Transactional
    public void deleteAccount(String token) {
        User user = getUserByToken(token);
        photoService.deletePhoto(user.getPhoto());
        userRepository.delete(user);
    }

    // 조회
    @Transactional(readOnly = true)
    public UserInfoDto getUserInfo(String token) {
        User user = getUserByToken(token);
        String imageUrl = null;
        if (user.getPhoto() != null) {
            imageUrl = photoService.getCDNUrl("user/" + user.getId(), user.getPhoto().getFileName());
        }
        return UserInfoDto.of(user, imageUrl);
    }

    // 모두 조회
    @Transactional(readOnly = true)
    public List<UserInfoDto> getAllUserInfo() {
        List<UserInfoDto> users = new ArrayList<>();
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            String imageUrl = null;
            if (user.getPhoto() != null)
                imageUrl = photoService.getCDNUrl("user/" + user.getId(), user.getPhoto().getFileName());
            users.add(UserInfoDto.of(user, imageUrl));
        }
        return users;
    }

    // 사진 업데이트
    @Transactional
    public UserInfoDto updatePhoto(String token, String photoName) {
        User user = getUserByToken(token);

        String imageUrl = null;
        if (user.getPhoto() != null) {
            if (photoName != null && !photoName.equals(user.getPhoto().getFileName())) {
                photoService.deletePhoto(user.getPhoto());
                PhotoResponseDto photo = photoService.createPhoto(PresignedUrlDto.builder()
                        .fileName(photoName)
                        .prefix("user/" + user.getId()).build());
                imageUrl = photo.getUrl();
            } else {
                imageUrl = user.getPhoto().getFileName();
            }
        } else {
            if (photoName != null) {
                PhotoResponseDto photo = photoService.createPhoto(PresignedUrlDto.builder()
                        .fileName(photoName)
                        .prefix("user/" + user.getId()).build());
                imageUrl = photo.getUrl();
            }
        }
        return UserInfoDto.of(user, imageUrl);
    }

}
