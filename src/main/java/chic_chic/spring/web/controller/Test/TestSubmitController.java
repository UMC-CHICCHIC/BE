package chic_chic.spring.web.controller.Test;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.service.testsubmitservice.TestSubmitService;
import chic_chic.spring.web.dto.ai.RecommendedProduct;
import chic_chic.spring.web.dto.ai.TestSubmitRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "TestSubmit", description = "테스트 결과 전달")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestSubmitController {

    private final TestSubmitService testSubmitService;

    @PostMapping
    public ApiResponse<List<RecommendedProduct>> submitTest(
            @RequestBody TestSubmitRequest request,
            @AuthenticationPrincipal User user) {

        //  로그인 여부에 따라 이메일 추출 또는 디버그용 이메일 설정
        String email = (user != null) ? user.getUsername() : "debug@example.com";

        return ApiResponse.onSuccess(
                testSubmitService.recommendAndSave(request.getAnswers(), email)
        );
    }
}
