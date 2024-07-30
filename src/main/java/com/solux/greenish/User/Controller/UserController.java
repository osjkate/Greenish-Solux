package com.solux.greenish.User.Controller;

import com.solux.greenish.Response.BasicResponse;
import com.solux.greenish.Response.DataResponse;
import com.solux.greenish.User.Dto.UserDto.*;
import com.solux.greenish.User.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    // 회원 가입
    @PostMapping(path = "/signUp", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> signUp(
            @Valid @RequestBody UserRegistDto request
    ) {
        return ResponseEntity.ok().body(
                new DataResponse<IdResponse>(userService.signUp(request)));
    }

    // 이메일 중복 확인
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailDuplicate(@RequestParam(value="email") String email) {
        return ResponseEntity.ok(userService.isEmailDuplicate(email));
    }

    // 닉네임 중복 확인
    @GetMapping("/check-nickname")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestParam(value="nickname") String nickname) {
        return ResponseEntity.ok(userService.isNicknameDuplicate(nickname));
    }

    // 계정 삭제
    @DeleteMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(@RequestParam(name = "userId") Long userId) {
        userService.deleteAccount(userId);
        return ResponseEntity.ok("계정 삭제 완료");
    }

    // 회원 정보 조회
    @GetMapping("/user-info")
    public ResponseEntity<UserInfoDto> getUserInfo(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getUserInfo(token));
    }

    @GetMapping
    public ResponseEntity<List<UserInfoDto>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUserInfo());
    }

}
