package chic_chic.spring.service.TestSubmitService;

import chic_chic.spring.client.AIRecommendClient;
import chic_chic.spring.converter.TestResultConverter;
import chic_chic.spring.domain.TestResult;
import chic_chic.spring.domain.repository.TestResultRepository;
import chic_chic.spring.web.dto.AIRequestDto;
import chic_chic.spring.web.dto.AIResponseDto;
import chic_chic.spring.web.dto.AnswerDto;
import chic_chic.spring.web.dto.RecommendedPerfumeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestSubmitServiceImpl implements TestSubmitService {

    private final AIRecommendClient aiRecommendClient;
    private final TestResultRepository testResultRepository;
    private final TestResultConverter testResultConverter;
    private final ObjectMapper objectMapper;

    @Transactional
    @Override
    public List<RecommendedPerfumeDto> recommendAndSave(List<AnswerDto> answers, Long userId) {
        AIResponseDto aiResponse = aiRecommendClient.sendRecommendRequest(answers);

        List<TestResult> savedResults = aiResponse.getRecommendedPerfumes().stream()
                .map(perfume -> {
                    String notesJson;
                    try {
                        notesJson = objectMapper.writeValueAsString(perfume.getRecommendedNotes());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("JSON 변환 실패", e);
                    }
                    return new TestResult(
                            perfume.getPerfumeId(),
                            userId,
                            perfume.getPerfumeName(),
                            notesJson,
                            LocalDateTime.now()
                    );
                }).toList();

        testResultRepository.saveAll(savedResults);

        return aiResponse.getRecommendedPerfumes().stream()
                .map(testResultConverter::toDto)
                .toList();
    }

    public AIRequestDto convertToAIRequest(List<AnswerDto> answers) {
        String gender = null, concentration = null, scents = null, baseNote = null, middleNote = null;

        for (AnswerDto answer : answers) {
            switch (answer.getQuestionId()) {
                case 1 -> gender = switch (answer.getOptionId()) {
                    case 1 -> "men";
                    case 2 -> "women";
                    case 3 -> "unisex";
                    case 4 -> "any";
                    default -> null;
                };
                case 2 -> concentration = switch (answer.getOptionId()) {
                    case 1 -> "edt";
                    case 2 -> "edp";
                    case 3 -> "parfum";
                    case 4 -> "any";
                    default -> null;
                };
                case 3 -> scents = switch (answer.getOptionId()) {
                    case 1 -> "woody";
                    case 2 -> "floral";
                    case 3 -> "fresh";
                    case 4 -> "sweet";
                    default -> null;
                };
                case 4 -> baseNote = switch (answer.getOptionId()) {
                    case 1 -> "sandalwood";
                    case 2 -> "musk";
                    case 3 -> "amber";
                    case 4 -> "vetiver";
                    default -> null;
                };
                case 5 -> middleNote = switch (answer.getOptionId()) {
                    case 1 -> "jasmine";
                    case 2 -> "rose";
                    case 3 -> "spicy";
                    case 4 -> "fruity";
                    default -> null;
                };
            }
        }

        return new AIRequestDto(gender, concentration, scents, baseNote, middleNote);
    }
}