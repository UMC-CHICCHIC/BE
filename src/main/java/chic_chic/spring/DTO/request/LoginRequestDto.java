package chic_chic.spring.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "로그인 요청 DTO")
public class LoginRequestDto {

    @Schema(description = "사용자 ID", example = "userid123")
    @NotBlank(message = "ID는 필수입니다.")
    private String userId;

    @Schema(description = "비밀번호", example = "securePassword!")
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    // Getter & Setter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}