package chic_chic.spring.client;

import chic_chic.spring.web.dto.AIRequestDto;
import chic_chic.spring.web.dto.AIResponseDto;
import chic_chic.spring.web.dto.AnswerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Component
@RequiredArgsConstructor
public class AIRecommendClient {

    private final RestTemplate restTemplate;

    public AIResponseDto sendRecommendRequest(List<AnswerDto> answers) {

        // AnswerDto → AIRequestDto로 변환
        AIRequestDto request = convertToAIRequest(answers);

        return restTemplate.postForObject(
                "http://192.168.2.19:8000/recommend_by_test",
                request,
                AIResponseDto.class
        );
    }

    // 내부 매핑 함수
    private AIRequestDto convertToAIRequest(List<AnswerDto> answers) {
        String gender = null, concentration = null, scents = null, baseNote = null;
        List<String> middleNotes = new ArrayList<>();

        for (AnswerDto answer : answers) {
            switch (answer.getQuestionId()) {
                case 1 -> gender = switch (answer.getOptionId()) {
                    case 101 -> "men"; case 102 -> "women"; case 103 -> "unisex"; case 104 -> "any"; default -> null;
                };
                case 2 -> concentration = switch (answer.getOptionId()) {
                    case 201 -> "edt"; case 202 -> "edp"; case 203 -> "parfum"; case 204 -> "any"; default -> null;
                };
                case 3 -> scents = switch (answer.getOptionId()) {
                    case 301 -> "woody"; case 302 -> "floral"; case 303 -> "fresh"; case 304 -> "sweet"; default -> null;
                };
                case 4 -> baseNote = switch (answer.getOptionId()) {
                    case 401 -> "sandalwood"; case 402 -> "musk"; case 403 -> "amber"; case 404 -> "vetiver"; default -> null;
                };
                case 5 -> middleNotes.add(switch (answer.getOptionId()) {
                    case 501 -> "jasmine"; case 502 -> "rose"; case 503 -> "spicy"; case 504 -> null; default -> null;
                });
                case 6 -> middleNotes.add(switch (answer.getOptionId()) {
                    case 601 -> "fruity"; case 602 -> "citrus"; case 603 -> "green"; case 604 -> null; default -> null;
                });
                case 7 -> middleNotes.add(switch (answer.getOptionId()) {
                    case 701 -> "powdery"; case 702 -> "soapy"; case 703 -> "clean"; case 704 -> null; default -> null;
                });
            }
        }

        // null 값 제거
        middleNotes.removeIf(note -> note == null);

        return new AIRequestDto(gender, concentration, scents, baseNote, middleNotes);
    }

}
