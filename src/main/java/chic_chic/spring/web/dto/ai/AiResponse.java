package chic_chic.spring.web.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AiResponse {
    private List<AIRecommendedPerfume> recommendedPerfumes;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AIRecommendedPerfume {
        private Long productId;
        private String perfumeName;
        private List<String> recommendedNotes;

        @Setter
        private String imageUrl;
    }
}
