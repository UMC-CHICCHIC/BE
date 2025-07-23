package chic_chic.spring.web.controller;

import chic_chic.spring.web.dto.MemberRequestDTO;
import chic_chic.spring.web.dto.MemberResponseDTO;
import chic_chic.spring.service.MemberService.MemberCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class MemberController {

    private final MemberCommandService memberService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDTO.JoinResultDTO> signup(@RequestBody @Valid MemberRequestDTO.JoinDto joinDto) {
        MemberResponseDTO.JoinResultDTO result = memberService.signup(joinDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberResponseDTO.LoginResultDTO> login(@RequestBody MemberRequestDTO.LoginDto loginDto) {
        MemberResponseDTO.LoginResultDTO loginResult = memberService.login(loginDto);
        return ResponseEntity.ok(loginResult);
    }

}
