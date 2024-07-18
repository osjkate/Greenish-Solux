package com.solux.greenish.User.Repository;

<<<<<<< HEAD:src/main/java/com/solux/greenish/Repository/UserRepository.java
import com.solux.greenish.Domain.SocialType;
import com.solux.greenish.Domain.User;
=======
import com.solux.greenish.User.Domain.User;
>>>>>>> c80c9e23e67256df331b7f343d4bbcf9b535d75c:src/main/java/com/solux/greenish/User/Repository/UserRepository.java
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);
<<<<<<< HEAD:src/main/java/com/solux/greenish/Repository/UserRepository.java

    Optional<User> findByRefreshToken(String refreshToken);

    /**
     * 소셜 타입과 소셜의 식별값으로 회원 찾는 메소드
     * 정보 제공을 동의한 순간 DB에 저장해야하지만, 아직 추가 정보(사는 도시, 나이 등)를 입력받지 않았으므로
     * 유저 객체는 DB에 있지만, 추가 정보가 빠진 상태이다.
     * 따라서 추가 정보를 입력받아 회원 가입을 진행할 때 소셜 타입, 식별자로 해당 회원을 찾기 위한 메소드
     */
    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
=======
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
>>>>>>> c80c9e23e67256df331b7f343d4bbcf9b535d75c:src/main/java/com/solux/greenish/User/Repository/UserRepository.java
}
