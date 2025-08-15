package chic_chic.spring.web.dto;

import chic_chic.spring.web.dto.ai.TestAnswerRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TestSubmitRequest {
    private List<TestAnswerRequest> answers;
}