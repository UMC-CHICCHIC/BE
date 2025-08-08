package chic_chic.spring.web.dto;

import lombok.*;

import java.time.LocalDateTime;

public class MemberResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResultDTO {
        private Long memberId;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResultDTO {
        private Long memberId;
        private String accessToken;
        private String refreshToken;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberInfoDTO {
        private String email;
        private String phoneNumber;
        private String nickname;

    }

    @Getter
    @Setter
    @Builder
    public static class UpdateResultDto {
        private String nickname;
        private String phoneNumber;
    }
}


