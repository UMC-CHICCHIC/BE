package chic_chic.spring.web.controller;


import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.service.TestSubmitService.TestSubmitService;
import chic_chic.spring.web.dto.RecommendedPerfumeDto;
import chic_chic.spring.web.dto.TestSubmitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import chic_chic.spring.domain.Member;
import chic_chic.spring.domain.repository.MemberRepository;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestSubmitController {

    private final TestSubmitService testSubmitService;
    private final MemberRepository memberRepository;  // 추가

    @PostMapping
    public ApiResponse<List<RecommendedPerfumeDto>> submitTest(
            @RequestBody TestSubmitRequest request,
            @AuthenticationPrincipal User user) {

        Long userId = null; // * 테스트를 위해 null 터리

        if (user != null) {
            Member member = memberRepository.findByEmail(user.getUsername())
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
            userId = member.getId();
        }

        return ApiResponse.onSuccess(
                testSubmitService.recommendAndSave(request.getAnswers(), userId)
        );
    }

}