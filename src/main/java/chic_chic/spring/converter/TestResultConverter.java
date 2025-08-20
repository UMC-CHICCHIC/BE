package chic_chic.spring.converter;

import chic_chic.spring.web.dto.ai.AiResponse;
import chic_chic.spring.web.dto.ai.RecommendedProduct;
import org.springframework.stereotype.Component;

@Component
public class TestResultConverter {

    public RecommendedProduct toDto(AiResponse.AIRecommendedPerfume aiPerfume) {
        return new RecommendedProduct(
                aiPerfume.getProductId(),
                aiPerfume.getPerfumeName(),
                aiPerfume.getRecommendedNotes(),
                aiPerfume.getImageUrl()
        );
    }
}