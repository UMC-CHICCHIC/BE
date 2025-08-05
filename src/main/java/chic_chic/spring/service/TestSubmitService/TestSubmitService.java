package chic_chic.spring.service.TestSubmitService;

import chic_chic.spring.web.dto.AnswerDto;
import chic_chic.spring.web.dto.RecommendedPerfumeDto;

import java.util.List;

public interface TestSubmitService {
    List<RecommendedPerfumeDto> recommendAndSave(List<AnswerDto> answers, Long memberId);
}
