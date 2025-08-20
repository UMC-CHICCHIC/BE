package chic_chic.spring.web.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReIssueResponseDTO {
    private String accessToken;
    private String refreshToken;
}