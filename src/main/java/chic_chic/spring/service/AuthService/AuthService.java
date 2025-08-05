package chic_chic.spring.service.AuthService;

import chic_chic.spring.web.dto.ReIssueResponseDTO;

public interface AuthService {
    ReIssueResponseDTO reissue(String refreshToken);
}
