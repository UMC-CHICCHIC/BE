package chic_chic.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AIResponseDto {
    private List<AIRecommendedPerfume> recommendedPerfumes;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AIRecommendedPerfume {
        private  Long productId;
        private String perfumeName;
        private List<String> recommendedNotes;
    }
}