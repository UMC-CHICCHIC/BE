package chic_chic.spring.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "회원가입 요청 DTO")
public class SignupRequestDto {

    @Schema(description = "아이디 (6~12자 이내)", example = "chicchic12")
    @NotBlank(message = "아이디는 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,12}$", message = "아이디는 영문과 숫자를 포함한 6~12자여야 합니다.")
    private String userId;

    @Schema(description = "비밀번호 (영문, 숫자, 특수문자 중 2가지 이상 조합 / 8~20자)", example = "Password123")
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d|.*\\W).{8,20}$",
            message = "비밀번호는 영문, 숫자, 특수문자 중 2가지 이상 조합의 8~20자여야 합니다."
    )
    private String password;

    @Schema(description = "비밀번호 확인", example = "Pa$$word123")
    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    private String passwordCheck;

    @Schema(description = "이메일 주소", example = "chicchic@example.com")
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @Schema(description = "휴대폰 번호", example = "01012345678")
    @NotBlank(message = "휴대폰 번호는 필수입니다.")
    @Pattern(regexp = "^010\\d{8}$", message = "휴대폰 번호는 010으로 시작하는 11자리 숫자여야 합니다.")
    private String phoneNumber;

    @Schema(description = "닉네임", example = "플로랄공주")
    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;
}