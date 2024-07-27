package com.solux.greenish.User.Domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    ADMIN("ROLE_ADMIN", "관리자"),
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "사용자");

    private final String authority;
    private final String description;
}

