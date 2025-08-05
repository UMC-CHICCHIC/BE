package chic_chic.spring.web.dto;

import chic_chic.spring.validator.annotation.PasswordMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class MemberRequestDTO {

    @PasswordMatch
    @Getter
    public static class JoinDto {


        @NotBlank(message = "비밀번호는 필수입니다.")
        @Pattern(
                regexp = "^(?=(.*[a-zA-Z].*[0-9])|(?=.*[a-zA-Z].*[!@#$%^&*])|(?=.*[0-9].*[!@#$%^&*]))[a-zA-Z0-9!@#$%^&*]{8,20}$",
                message = "비밀번호는 영문, 숫자, 특수문자 중 2가지 이상 조합이며, 8자 ~20자 이내 이어야 합니다."
        )
        private String password;

        @NotBlank(message = "비밀번호 확인은 필수입니다.")
        private String passwordConfirm;  // 비밀번호 확인

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "유효한 이메일 형식이어야 합니다.")
        private String email;  // 이메일

        @NotBlank(message = "휴대전화 번호는 필수입니다.")
        @Pattern(regexp = "^010\\d{8}$", message = "휴대전화 번호는 010으로 시작하는 11자리 숫자여야 합니다.")
        private String phoneNumber;  // 휴대전화 번호

        @NotBlank(message = "닉네임은 필수입니다.")
        private String nickname;  // 닉네임
    }

    @Getter
    public static class LoginDto {
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "유효한 이메일 형식이어야 합니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수입니다.")
        private String password;
    }

}
