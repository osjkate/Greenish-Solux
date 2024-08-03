package com.solux.greenish.Environment.Service;

import com.solux.greenish.Environment.Domain.Environment;
import com.solux.greenish.Environment.Repository.EnvironmentRepository;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.User.Repository.UserRepository;
import com.solux.greenish.login.Jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnvironmentService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    @Autowired
    private EnvironmentRepository environmentRepository;

    @Transactional
    public void updateLocation(String token, Environment newenvironment) {
        String email = jwtUtil.getEmail(token.split(" ")[1]);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 조회할 수 없습니다."));

        Environment environment = user.getEnvironment();

        if (environment == null) {
            environment = new Environment();
            user.setEnvironment(environment);
        }

        environment.updateLocation(newenvironment.getLatitude(), newenvironment.getLongitude());
        userRepository.save(user);  // Save the updated user with environment
    }
}
