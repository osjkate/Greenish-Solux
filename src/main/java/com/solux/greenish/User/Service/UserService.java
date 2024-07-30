package com.solux.greenish.User.Service;

import com.solux.greenish.User.Dto.UserDto.*;
import com.solux.greenish.User.Repository.UserRepository;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.login.Jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Transactional
    public IdResponse signUp(@Valid UserRegistDto request) {
        if (isEmailDuplicate(request.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 이메일입니다. ");
        }
        if (isNicknameDuplicate(request.getNickname())) {
            throw new RuntimeException("이미 사용 중인 닉네임입니다.");
        }

        User user = request.toUser(bCryptPasswordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return IdResponse.of(user);
    }

    public void deleteAccount(String token) {
        String email = jwtUtil.getEmail(token.split(" ")[1]);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 조회할 수 없습니다."));
        userRepository.deleteById(user.getId());
    }

    public UserInfoDto getUserInfo(String token) {
        String email = jwtUtil.getEmail(token.split(" ")[1]);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return UserInfoDto.of(user);
    }

}
