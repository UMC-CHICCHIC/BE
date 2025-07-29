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
                        new OptionDto(101, "남성향"),
                        new OptionDto(102, "여성향"),
                        new OptionDto(103, "중성향"),
                        new OptionDto(104, "상관없음")
                )),
                new QuestionDto(2, "어떤 향수의 지속력을 선호하시나요?", List.of(
                        new OptionDto(201, "EDT (약한 지속력)"),
                        new OptionDto(202, "EDP (중간 지속력)"),
                        new OptionDto(203, "Parfum (강한 지속력)"),
                        new OptionDto(204, "상관없음")
                )),
                new QuestionDto(3, "어떤 향 계열이 가장 끌리시나요?", List.of(
                        new OptionDto(301, "우디"),
                        new OptionDto(302, "플로럴"),
                        new OptionDto(303, "프레시"),
                        new OptionDto(304, "스위트")
                )),
                new QuestionDto(4, "향이 남을 때 어떤 잔향을 기대하시나요?", List.of(
                        new OptionDto(401, "샌달우드"),
                        new OptionDto(402, "머스크"),
                        new OptionDto(403, "앰버"),
                        new OptionDto(404, "베티버")
                )),
                new QuestionDto(5, "향의 중심에서 느끼고 싶은 향은 어떤 것인가요?", List.of(
                        new OptionDto(501, "자스민"),
                        new OptionDto(502, "로즈"),
                        new OptionDto(503, "스파이시"),
                        new OptionDto(504, "해당 없음")
                )),
                new QuestionDto(6, "또 다른 선호 향이 있으신가요?", List.of(
                        new OptionDto(601, "프루티"),
                        new OptionDto(602, "시트러스"),
                        new OptionDto(603, "그린"),
                        new OptionDto(604, "해당 없음")
                )),
                new QuestionDto(7, "중성적이거나 파우더리한 향은 어떤가요?", List.of(
                        new OptionDto(701, "파우더리"),
                        new OptionDto(702, "비누향"),
                        new OptionDto(703, "클린"),
                        new OptionDto(704, "해당 없음")
                ))
        );
    }
}
