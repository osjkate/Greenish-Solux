package com.solux.greenish.Service;

import com.solux.greenish.Dto.PostDto.PostDetailDto;
import com.solux.greenish.Repository.PostRepository;
import com.solux.greenish.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public PostDetailDto postDetail(Long id) {
        return postRepository.findById(id)
                .map(PostDetailDto::of)
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재하지 않습니다. "));
    }

    @Transactional
    public String postCreatePost() {
        return "";
    }
}
