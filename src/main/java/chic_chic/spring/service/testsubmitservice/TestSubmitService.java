package chic_chic.spring.service.testsubmitservice;

import chic_chic.spring.client.AIRecommendClient;
import chic_chic.spring.converter.TestResultConverter;
import chic_chic.spring.domain.entity.Product;
import chic_chic.spring.domain.entity.TestResult;
import chic_chic.spring.domain.repository.ProductRepository;
import chic_chic.spring.domain.repository.TestResultRepository;
import chic_chic.spring.web.dto.ai.AiResponse;
import chic_chic.spring.web.dto.ai.RecommendedProduct;
import chic_chic.spring.web.dto.ai.TestAnswerRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestSubmitService {

    private final AIRecommendClient aiRecommendClient;
    private final TestResultRepository testResultRepository;
    private final TestResultConverter testResultConverter;
    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;

    @Transactional
    public List<RecommendedProduct> recommendAndSave(List<TestAnswerRequest> answers, String memberEmail) {
        AiResponse aiResponse = aiRecommendClient.sendRecommendRequest(answers);
        if (aiResponse == null || aiResponse.getRecommendedPerfumes() == null) {
            return List.of();
        }

        List<Long> ids = aiResponse.getRecommendedPerfumes().stream()
                .map(AiResponse.AIRecommendedPerfume::getProductId)
                .filter(Objects::nonNull)
                .toList();

        if (!ids.isEmpty()) {
            Map<Long, String> idToImageUrl = productRepository.findAllById(ids).stream()
                    .collect(Collectors.toMap(
                            Product::getProductId,
                            Product::getImageUrl,
                            (a, b) -> a
                    ));

            aiResponse.getRecommendedPerfumes().forEach(p ->
                    p.setImageUrl(idToImageUrl.get(p.getProductId()))
            );
        }

        List<TestResult> savedResults = aiResponse.getRecommendedPerfumes().stream()
                .map(perfume -> {
                    String notesJson;
                    try {
                        notesJson = objectMapper.writeValueAsString(perfume.getRecommendedNotes());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("노트 JSON 변환 실패", e);
                    }
                    return new TestResult(
                            perfume.getProductId(),
                            memberEmail,
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
}
