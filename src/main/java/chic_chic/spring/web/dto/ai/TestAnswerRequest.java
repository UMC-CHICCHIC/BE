package chic_chic.spring.web.dto.ai;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestAnswerRequest {
    private Integer questionId;
    private Integer optionId;
}