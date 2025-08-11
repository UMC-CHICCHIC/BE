package chic_chic.spring.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConsultPostCommentsRequest {
    @NotBlank
    private String content;
}
