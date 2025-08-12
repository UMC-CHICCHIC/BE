package chic_chic.spring.web.controller;


import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.converter.TestQuestionConverter;
import chic_chic.spring.web.dto.QuestionDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Test", description = "테스트지 질문")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestQuestionController {

    //질문지는 하드코딩해서 Service 구현 따로 x

    private final TestQuestionConverter testQuestionConverter;

    @GetMapping("/questions")
    public ApiResponse<List<QuestionDto>> getQuestions() {
        return ApiResponse.onSuccess(testQuestionConverter.getAllQuestions());
    }
}
