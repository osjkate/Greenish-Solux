package com.solux.greenish.Post.Repository;

import com.solux.greenish.Post.Domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserId(Long userId);
    List<Post> findAllByPlantId(Long plantId);
}
