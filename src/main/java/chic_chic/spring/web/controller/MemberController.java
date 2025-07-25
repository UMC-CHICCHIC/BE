package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.web.dto.MemberRequestDTO;
import chic_chic.spring.web.dto.MemberResponseDTO;
import chic_chic.spring.service.MemberService.MemberCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class MemberController {

    private final MemberCommandService memberService;
    private final MemberCommandService memberCommandService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDTO.JoinResultDTO> signup(@RequestBody @Valid MemberRequestDTO.JoinDto joinDto) {
        MemberResponseDTO.JoinResultDTO result = memberService.signup(joinDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberResponseDTO.LoginResultDTO>> login(@RequestBody MemberRequestDTO.LoginDto loginDto) {
        MemberResponseDTO.LoginResultDTO loginResult = memberService.login(loginDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(loginResult));
    }

    @GetMapping("/info")
    @Operation(summary = "유저 내 정보 조회 API - 인증 필요",
            description = "유저가 내 정보를 조회하는 API입니다.",
            security = { @SecurityRequirement(name = "JWT TOKEN") }
    )
    public ApiResponse<MemberResponseDTO.MemberInfoDTO> getMyInfo(HttpServletRequest request) {
        return ApiResponse.onSuccess(memberCommandService.getMemberInfo(request));
    }

}
