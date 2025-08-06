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
@RequiredArgsConstructor
public class MemberController {

    private final MemberCommandService memberService;

    @PostMapping("/api/v1/auth/signup")
    @Operation(summary = "회원가입 API", description = "회원가입을 처리하는 API입니다.")
    public ResponseEntity<MemberResponseDTO.JoinResultDTO> signup(@RequestBody @Valid MemberRequestDTO.JoinDto joinDto) {
        MemberResponseDTO.JoinResultDTO result = memberService.signup(joinDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/api/v1/auth/login")
    @Operation(summary = "로그인 API", description = "사용자 로그인을 처리하는 API입니다.")
    public ResponseEntity<ApiResponse<MemberResponseDTO.LoginResultDTO>> login(@RequestBody MemberRequestDTO.LoginDto loginDto) {
        MemberResponseDTO.LoginResultDTO loginResult = memberService.login(loginDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(loginResult));
    }

    @GetMapping("/member/info")
    @Operation(
            summary = "유저 내 정보 조회 API - 인증 필요",
            description = "유저가 내 정보를 조회하는 API입니다.",
            security = {@SecurityRequirement(name = "JWT")}
    )
    public ApiResponse<MemberResponseDTO.MemberInfoDTO> getMyInfo(HttpServletRequest request) {
        return ApiResponse.onSuccess(memberService.getMemberInfo(request));
    }

    @PutMapping("/member/info")
    @Operation(
            summary = "회원 정보 수정 API - 인증 필요",
            description = "소셜 로그인 회원과 일반 회원은 닉네임, 휴대전화 수정이 가능한 API입니다.",
            security = {@SecurityRequirement(name = "JWT")}
    )
    public ResponseEntity<ApiResponse<String>> updateMyInfo(
            @RequestBody @Valid MemberRequestDTO.UpdateDto updateDto,
            HttpServletRequest request
    ) {
        memberService.updateMemberInfo(updateDto, request);
        return ResponseEntity.ok(ApiResponse.onSuccess("회원 정보가 수정되었습니다."));
    }

    @DeleteMapping("/member/info")
    @Operation(
            summary = "회원 탈퇴 API - 인증 필요",
            description = "회원 탈퇴 기능 API입니다.",
            security = {@SecurityRequirement(name = "JWT")}
    )
    public ResponseEntity<?> withdraw(HttpServletRequest request) {
        memberService.withdraw(request);
        return ResponseEntity.ok(ApiResponse.onSuccess("회원 탈퇴가 완료되었습니다."));
    }

    @DeleteMapping("/member/logout")
    @Operation(
            summary = "회원 로그아웃 API - 인증 필요",
            description = "회원 로그아웃 기능 API입니다.",
            security = {@SecurityRequirement(name = "JWT")}
    )
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request) {
        memberService.logout(request);
        return ResponseEntity.ok(ApiResponse.onSuccess("로그아웃이 되었습니다."));
    }

}
