package chic_chic.spring.converter;

import chic_chic.spring.web.dto.AIResponseDto;
import chic_chic.spring.web.dto.RecommendedPerfumeDto;
import org.springframework.stereotype.Component;

@Component
public class TestResultConverter {

    public RecommendedPerfumeDto toDto(AIResponseDto.AIRecommendedPerfume aiPerfume) {
        return new RecommendedPerfumeDto(
                aiPerfume.getPerfumeId(),
                aiPerfume.getPerfumeName(),
                aiPerfume.getRecommendedNotes()
        );
    }
}