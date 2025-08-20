package chic_chic.spring.web.dto.perfumereview;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequest {
    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    @NotBlank
    private String content;
}