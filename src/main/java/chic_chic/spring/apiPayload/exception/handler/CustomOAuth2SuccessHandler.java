package chic_chic.spring.apiPayload.exception.handler;

import chic_chic.spring.service.AuthService.AuthService;
import chic_chic.spring.web.dto.ReIssueResponseDTO;
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

    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = extractEmailFromOAuth2User(oAuth2User);
        if (email == null) {
            throw new RuntimeException("이메일을 가져올 수 없습니다.");
        }

        // 로그인 처리(AccessToken + RefreshToken 생성 및 저장)
        ReIssueResponseDTO tokens = authService.login(email);

        response.setContentType("application/json;charset=UTF-8");
        String json = String.format("{\"accesstoken\": \"%s\", \"refreshToken\": \"%s\"}", tokens.getAccessToken(), tokens.getRefreshToken());
        response.getWriter().write(json);
        response.getWriter().flush();
    }

    private String extractEmailFromOAuth2User(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");

        if (email == null && oAuth2User.getAttributes().containsKey("kakao_account")) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
            if (kakaoAccount != null) {
                email = (String) kakaoAccount.get("email");
            }
        }

        if (email == null && oAuth2User.getAttributes().containsKey("response")) {
            Map<String, Object> responseObj = (Map<String, Object>) oAuth2User.getAttributes().get("response");
            if (responseObj != null) {
                email = (String) responseObj.get("email");
            }
        }
        return email;
    }
}
