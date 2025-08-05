package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.service.AuthService.AuthService;
import chic_chic.spring.web.dto.ReIssueRequestDTO;
import chic_chic.spring.web.dto.ReIssueResponseDTO;
import com.nimbusds.oauth2.sdk.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/member/reissue")
    public ResponseEntity<?> reissue(@RequestBody ReIssueRequestDTO request) {
        ReIssueResponseDTO tokenResponse = authService.reissue(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.onSuccess(tokenResponse));
    }
}
