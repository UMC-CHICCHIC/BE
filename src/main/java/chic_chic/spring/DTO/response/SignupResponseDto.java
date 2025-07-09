package chic_chic.spring.DTO.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupResponseDto {

    @Schema(description = "생성된 유저 ID", example = "1")
    private Long userId;

    @Schema(description = "회원가입한 유저 이메일", example = "test@example.com")
    private String email;
}