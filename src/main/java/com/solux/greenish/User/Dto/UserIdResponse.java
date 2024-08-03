package com.solux.greenish.User.Dto;

import com.solux.greenish.User.Domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserIdResponse {
    private Long userId;
    private String imageUrl;
}
