package com.solux.greenish.User.Controller;

import com.solux.greenish.Response.BasicResponse;
import com.solux.greenish.Response.DataResponse;
import com.solux.greenish.User.Dto.UserDto.*;
import com.solux.greenish.User.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    // 회원 가입
    // TODO: Spring Security
    @PostMapping(path = "/signUp", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> signUp(
            @Valid @RequestBody UserRegistDto request
    ) {
        return ResponseEntity.ok().body(
                new DataResponse<IdResponse>(userService.signUp(request)));
    }
}
