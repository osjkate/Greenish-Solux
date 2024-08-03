package com.solux.greenish.Post.Repository;

import com.solux.greenish.Post.Domain.Post;
import com.solux.greenish.User.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserId(Long userId);
    List<Post> findAllByPlantId(Long plantId);

    int countByUserIdAndCreatedAtAfter(Long userId, LocalDate createdAt);}
