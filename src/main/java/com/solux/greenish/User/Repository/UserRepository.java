package com.solux.greenish.User.Repository;

import com.solux.greenish.User.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

}



