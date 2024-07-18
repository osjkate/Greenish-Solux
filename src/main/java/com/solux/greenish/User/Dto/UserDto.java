package com.solux.greenish.User.Dto;

import com.solux.greenish.User.Domain.RoleType;
import com.solux.greenish.User.Domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
public class UserDto {


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IdResponse{
        private Long id;
        public static IdResponse of(User user) {
            return new IdResponse(user.getId());
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserRegistDto {
        @NotBlank
        private String nickname;

        @Builder.Default
        private RoleType role = RoleType.USER;

        @NotBlank
        private String password;


        @Email(message = "잘못된 이메일 형식입니다. ")
        private String email;


        public User toUser(String password) {
            return User.builder()
                    .nickname(nickname)
                    .password(password)
                    .role(role)
                    .email(email)
                    .build();
        }
    }
}
