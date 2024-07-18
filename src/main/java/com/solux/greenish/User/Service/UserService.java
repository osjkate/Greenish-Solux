package com.solux.greenish.User.Service;

import com.solux.greenish.User.Dto.UserDto.*;
import com.solux.greenish.User.Repository.UserRepository;
import com.solux.greenish.User.Domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public IdResponse signUp(UserRegistDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 이메일입니다. ");
        }

        User user = request.toUser(bCryptPasswordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return IdResponse.of(user);
    }
}
