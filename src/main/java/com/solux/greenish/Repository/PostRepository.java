package com.solux.greenish.Repository;

import com.solux.greenish.Domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserId(Long userId);
    List<Post> findAllByPlantId(Long plantId);
}
