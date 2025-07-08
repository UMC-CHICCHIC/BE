package chic_chic.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    // private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup() {
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok("로그인 성공");
    }

    @PostMapping("/oauth/login")
    public ResponseEntity<?> oauthLogin() {
        return ResponseEntity.ok("OAuth 로그인");
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok("토큰 재발급");
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String accessToken) {
        return ResponseEntity.ok("로그아웃 성공");
    }
}
