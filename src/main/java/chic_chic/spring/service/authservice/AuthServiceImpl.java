package chic_chic.spring.service.authservice;

import chic_chic.spring.apiPayload.exception.handler.CustomException;
import chic_chic.spring.config.jwt.JwtTokenProvider;
import chic_chic.spring.domain.entity.RefreshToken;
import chic_chic.spring.domain.repository.RefreshTokenRepository;
import chic_chic.spring.web.dto.ReIssueResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public ReIssueResponseDTO login(String email) {
        String accessToken = jwtTokenProvider.createAccessToken(email);
        String refreshToken = jwtTokenProvider.createRefreshToken(email);

        RefreshToken tokenEntity = RefreshToken.builder()
                .email(email)
                .token(refreshToken)
                .build();

        refreshTokenRepository.save(tokenEntity);

        return new ReIssueResponseDTO(accessToken, refreshToken);
    }

    @Override
    public ReIssueResponseDTO reissue(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new CustomException("Refresh Token이 유효하지 않습니다");
        }

        String email = jwtTokenProvider.getEmailFromToken(refreshToken);

        RefreshToken stored = refreshTokenRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("Refresh Token을 찾을 수 없습니다."));

        if (!stored.getToken().equals(refreshToken)) {
            throw new CustomException("Token이 맞지 않습니다.");
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(email);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(email);

        stored.updateToken(newRefreshToken);
        refreshTokenRepository.save(stored);

        return new ReIssueResponseDTO(newAccessToken, newRefreshToken);
    }
}
