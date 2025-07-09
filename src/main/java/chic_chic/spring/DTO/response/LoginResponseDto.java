package chic_chic.spring.DTO.response;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 응답 DTO")
public class LoginResponseDto {

    @Schema(description = "로그인 성공 여부", example = "true")
    private boolean success;

    @Schema(description = "응답 메시지", example = "로그인 성공!")
    private String message;

    public LoginResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
