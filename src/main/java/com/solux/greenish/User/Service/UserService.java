package com.solux.greenish.User.Service;

import com.solux.greenish.Photo.Domain.Photo;
import com.solux.greenish.Photo.Dto.PhotoResponseDto;
import com.solux.greenish.Photo.Dto.PresignedUrlDto;
import com.solux.greenish.Photo.Repository.PhotoRepository;
import com.solux.greenish.Photo.Service.PhotoService;
import com.solux.greenish.User.Dto.UserDto.*;
import com.solux.greenish.User.Repository.UserRepository;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.login.Jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public IdResponse signUp(UserRegistDto request) {
        if (isEmailDuplicate(request.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 이메일입니다. ");
        }
        if (isNicknameDuplicate(request.getNickname())) {
            throw new RuntimeException("이미 사용 중인 닉네임입니다.");
        }

        User user = request.toUser(bCryptPasswordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        if (request.getFileName() != null) {

            PhotoResponseDto photo = photoService.createPhoto(
                    PresignedUrlDto.builder()
                            .prefix("user/:" + user.getId())
                            .fileName(request.getFileName()).build());
            user.updatePhoto(getPhoto(photo.getPhotoId()));
        }

        return IdResponse.of(user);
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
        return UserInfoDto.of(user, photoService.getCDNUrl("user/" + user.getId(), user.getPhoto().getFileName()));
    }

    // 모두 조회
    @Transactional(readOnly = true)
    public List<UserInfoDto> getAllUserInfo() {
        return userRepository.findAll().stream()
                .map((user) ->
                        UserInfoDto.of(user,
                                photoService.getCDNUrl("user/" + user.getId(),
                                        user.getPhoto().getFileName()))).toList();
    }

}
