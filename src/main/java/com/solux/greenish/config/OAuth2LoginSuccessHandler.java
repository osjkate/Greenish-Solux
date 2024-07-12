package com.solux.greenish.config;

import java.io.IOException;

import com.solux.greenish.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomLoginSuccessHandler.class);
    private final JwtUtil jwtUtil;

    public CustomLoginSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        String token = jwtUtil.generateToken(username);

        logger.info("User '{}' logged in successfully.", username);

        // You can add the token to the response header or body
        response.addHeader("Authorization", "Bearer " + token);

        // Alternatively, you can return a JSON response with the token
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"token\": \"" + token + "\"}");

        // Optionally, redirect to a success page
        setDefaultTargetUrl("/loginSuccess");
        super.onAuthenticationSuccess(request, response, authentication);
    }

}

