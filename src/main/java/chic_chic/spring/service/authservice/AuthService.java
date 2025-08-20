package chic_chic.spring.service.authservice;

import chic_chic.spring.web.dto.ReIssueResponseDTO;

public interface AuthService {
    ReIssueResponseDTO login(String email);        // ✅ 이 줄 추가
    ReIssueResponseDTO reissue(String refreshToken);
}
