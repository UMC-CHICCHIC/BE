package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.service.AuthService.AuthService;
import chic_chic.spring.web.dto.ReIssueRequestDTO;
import chic_chic.spring.web.dto.ReIssueResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/member/reissue")
    @Operation(summary = "토큰 재발급 API - 인증 필요", description = "리프레시 토큰을 통해 새로운 액세스 토큰과 리프레시 토큰을 발급합니다.")
    public ResponseEntity<?> reissue(@RequestBody ReIssueRequestDTO request) {
        ReIssueResponseDTO tokenResponse = authService.reissue(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.onSuccess(tokenResponse));
    }
}
