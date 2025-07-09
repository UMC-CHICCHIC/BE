package chic_chic.spring.Controller;

import chic_chic.spring.DTO.request.SignupRequestDto;
import chic_chic.spring.DTO.response.SignupResponseDto;
import chic_chic.spring.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class TestUserLoginController {

    @Operation(summary = "회원가입 API", description = "회원 정보를 받아 회원가입을 진행합니다.")
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<SignupResponseDto>> signup(@Valid @RequestBody SignupRequestDto request) {
        // 실제 DB 저장 로직 생략
        SignupResponseDto response = new SignupResponseDto(1L, request.getEmail());

        return ResponseEntity.ok(
                new CommonResponse<>(true, "회원가입 성공!", response)
        );
    }
}