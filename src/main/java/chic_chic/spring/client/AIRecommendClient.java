package chic_chic.spring.client;

import chic_chic.spring.web.dto.AIRequestDto;
import chic_chic.spring.web.dto.AIResponseDto;
import chic_chic.spring.web.dto.AnswerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Component
@RequiredArgsConstructor
public class AIRecommendClient {

    private final RestTemplate restTemplate;

    public AIResponseDto sendRecommendRequest(List<AnswerDto> answers) {

        // AnswerDto → AIRequestDto로 변환
        AIRequestDto request = convertToAIRequest(answers);

        return restTemplate.postForObject(
                "",
                request,
                AIResponseDto.class
        );
    }

    // 내부 매핑 함수
    private AIRequestDto convertToAIRequest(List<AnswerDto> answers) {
        String gender = null, concentration = null, scents = null, baseNote = null, middleNote = null;

        for (AnswerDto answer : answers) {
            switch (answer.getQuestionId()) {
                case 1 -> gender = switch (answer.getOptionId()) {
                    case 1 -> "men"; case 2 -> "women"; case 3 -> "unisex"; case 4 -> "any"; default -> null;
                };
                case 2 -> concentration = switch (answer.getOptionId()) {
                    case 1 -> "edt"; case 2 -> "edp"; case 3 -> "parfum"; case 4 -> "any"; default -> null;
                };
                case 3 -> scents = switch (answer.getOptionId()) {
                    case 1 -> "woody"; case 2 -> "floral"; case 3 -> "fresh"; case 4 -> "sweet"; default -> null;
                };
                case 4 -> baseNote = switch (answer.getOptionId()) {
                    case 1 -> "sandalwood"; case 2 -> "musk"; case 3 -> "amber"; case 4 -> "vetiver"; default -> null;
                };
                case 5 -> middleNote = switch (answer.getOptionId()) {
                    case 1 -> "jasmine"; case 2 -> "rose"; case 3 -> "spicy"; case 4 -> "fruity"; default -> null;
                };
            }
        }

        return new AIRequestDto(gender, concentration, scents, baseNote, middleNote);
    }
}

