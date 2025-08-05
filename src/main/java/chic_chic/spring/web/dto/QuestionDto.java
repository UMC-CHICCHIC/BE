package chic_chic.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QuestionDto {
    private Integer questionId;
    private String text;
    private List<OptionDto> options;
}