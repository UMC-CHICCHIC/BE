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
                new QuestionDto(2, "어느 정도의 지속력을 원하시나요?", List.of(
                        new OptionDto(201, "EDT (약한 지속력)"),
                        new OptionDto(202, "EDP (중간 지속력)"),
                        new OptionDto(203, "Parfum (강한 지속력)"),
                        new OptionDto(204, "상관없음")
                )),
                new QuestionDto(3, " 어떤 계열의 향수가 가장 끌리시나요?", List.of(
                        new OptionDto(301, "우디"),
                        new OptionDto(302, "플로럴"),
                        new OptionDto(303, "프레시"),
                        new OptionDto(304, "스위트")
                )),
                new QuestionDto(4, "향수를 뿌린지 일정 시간이 지난 후 남는 잔향은 어떤게 좋으신가요?", List.of(
                        new OptionDto(401, "샌달우드"),
                        new OptionDto(402, "머스크"),
                        new OptionDto(403, "앰버"),
                        new OptionDto(404, "베티버")
                )),
                new QuestionDto(5, "평소 어떤 향을 가장 좋아하시나요?", List.of(
                        new OptionDto(501, "자스민"),
                        new OptionDto(502, "로즈"),
                        new OptionDto(503, "스파이시"),
                        new OptionDto(504, "해당 없음")
                )),
                new QuestionDto(6, "앞의 질문과 이어 평소 어떤 향을 가장 좋아하시나요?", List.of(
                        new OptionDto(601, "프루티"),
                        new OptionDto(602, "시트러스"),
                        new OptionDto(603, "그린"),
                        new OptionDto(604, "해당 없음")
                )),
                new QuestionDto(7, "아래의 향 중 본인의 취향이 있으신가요?", List.of(
                        new OptionDto(701, "파우더리"),
                        new OptionDto(702, "비누향"),
                        new OptionDto(703, "클린"),
                        new OptionDto(704, "해당 없음")
                ))
        );
    }
}
