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
        @NotBlank(message = "닉네임을 입력해주세요.")
        private String nickname;

        @Builder.Default
        private RoleType role = RoleType.USER;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;


        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "잘못된 이메일 형식입니다.")
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

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoDto {
        private Long id;
        private String nickname;
        private String email;

        public static UserInfoDto of(User user) {
            return UserInfoDto.builder()
                    .id(user.getId())
                    .nickname(user.getNickname())
                    .email(user.getEmail())
                    .build();
        }
    }

}
