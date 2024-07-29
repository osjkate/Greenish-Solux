package com.solux.greenish.Environment.Service;

import com.solux.greenish.Environment.Domain.Environment;
import com.solux.greenish.Environment.Repository.EnvironmentRepository;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnvironmentService {
    private final UserRepository userRepository;
    @Autowired
    private EnvironmentRepository environmentRepository;

    @Transactional
    public void updateLocation(Long userId, Environment newEnvironmentData) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + userId));

        Environment environment = user.getEnvironment();

        if (environment == null) {
            environment = new Environment();
            user.setEnvironment(environment);
        }

        environment.updateLocation(newEnvironmentData.getLatitude(), newEnvironmentData.getLongitude());
        userRepository.save(user);  // Save the updated user with environment
    }
}
