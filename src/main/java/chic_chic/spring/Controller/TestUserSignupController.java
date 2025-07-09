package chic_chic.spring.Controller;

import chic_chic.spring.DTO.request.SignupRequestDto;
import chic_chic.spring.DTO.response.SignupResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class TestUserSignupController {

    @Operation(summary = "회원가입 API", description = "회원 정보를 받아 회원가입을 진행합니다.")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto response = new SignupResponseDto(true, "회원가입 요청이 성공적으로 들어왔습니다!");
        return ResponseEntity.ok(response);
    }
}