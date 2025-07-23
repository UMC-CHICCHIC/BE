package chic_chic.spring.converter;

import chic_chic.spring.web.dto.OptionDto;
import chic_chic.spring.web.dto.QuestionDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestQuestionConverter {

    public List<QuestionDto> getAllQuestions() {
        return List.of(
                new QuestionDto(1, "평소 어떤 성별 계열의 향수를 선호하시나요?", List.of(
                        new OptionDto(101, "Man"),
                        new OptionDto(102, "Woman"),
                        new OptionDto(103, "Unisex"),
                        new OptionDto(104, "Any")
                )),
                new QuestionDto(2, "어떤 향수의 지속력을 선호하시나요?", List.of(
                        new OptionDto(201, "EDT"),
                        new OptionDto(202, "EDP"),
                        new OptionDto(203, "Parfum"),
                        new OptionDto(204, "Any")
                )),
                new QuestionDto(3, "어떤 향 계열이 가장 끌리시나요?", List.of(
                        new OptionDto(301, "Woody"),
                        new OptionDto(302, "Floral"),
                        new OptionDto(303, "Fresh"),
                        new OptionDto(304, "Sweet")
                )),
                new QuestionDto(4, "향이 남을 때 어떤 잔향을 기대하시나요?", List.of(
                        new OptionDto(401, "Sandalwood"),
                        new OptionDto(402, "Musk"),
                        new OptionDto(403, "Amber"),
                        new OptionDto(404, "Vetiver")
                )),
                new QuestionDto(5, "향의 중심에서 느끼고 싶은 향은 어떤 것인가요?", List.of(
                        new OptionDto(501, "Jasmine"),
                        new OptionDto(502, "Rose"),
                        new OptionDto(503, "Spicy"),
                        new OptionDto(504, "Fruity")
                ))
        );
    }
}
