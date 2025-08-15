package chic_chic.spring.web.dto.product;

import jakarta.validation.constraints.NotBlank;

public record PerfumeStoryRequest(
        @NotBlank String title,
        @NotBlank String summary,
        @NotBlank String content,
        String thumbnailUrl
) {}