package com.solux.greenish.controller;

// LoginController 클래스는 로그인 및 회원가입 완료 후 리디렉션할 컨트롤러와 뷰를 설정합니다.
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        // 로그인 페이지를 반환합니다.
        return "login";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(Model model) {
        // 로그인 성공 후 사용자 정보를 모델에 추가합니다.
        // 예: model.addAttribute("user", 사용자 정보);
        return "loginSuccess";
    }

    @GetMapping("/loginFailure")
    public String loginFailure() {
        // 로그인 실패 페이지를 반환합니다.
        return "loginFailure";
    }
}
