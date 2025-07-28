package chic_chic.spring.apiPayload.exception.handler;

import chic_chic.spring.config.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // registrationId 가져오기 - 올바른 방법
        String registrationId = (String) request.getAttribute("registrationId");
        // 또는, SecurityContextHolder 등 다른 방법으로 가져오기 필요

        // 이메일 추출 변수
        String email = null;

        // 먼저 email 직접 꺼내기 시도
        email = oAuth2User.getAttribute("email");

        // 카카오 로그인 이메일 처리
        if (email == null && oAuth2User.getAttributes().containsKey("kakao_account")) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
            if (kakaoAccount != null) {
                email = (String) kakaoAccount.get("email");
            }
        }

        // 네이버 로그인 이메일 처리 추가
        if (email == null && oAuth2User.getAttributes().containsKey("response")) {
            Map<String, Object> responseObj = (Map<String, Object>) oAuth2User.getAttributes().get("response");
            if (responseObj != null) {
                email = (String) responseObj.get("email");
            }
        }

        if (email == null) {
            throw new RuntimeException("이메일을 가져올 수 없습니다. provider 속성을 확인하세요.");
        }

        // JWT 토큰 생성 및 응답
        String token = jwtTokenProvider.createAccessToken(email);

        response.setContentType("application/json;charset=UTF-8");
        String json = "{\"token\": \"" + token + "\"}";
        response.getWriter().write(json);
        response.getWriter().flush();
    }
}
