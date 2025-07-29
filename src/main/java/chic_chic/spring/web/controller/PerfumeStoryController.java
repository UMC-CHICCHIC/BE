package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.ApiResponse;
import chic_chic.spring.web.dto.PerfumeStoryDetailResponse;
import chic_chic.spring.web.dto.PerfumeStoryResponse;
import chic_chic.spring.service.perfumestoryservice.PerfumeStoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/perfume-stories")
public class PerfumeStoryController {

    private final PerfumeStoryService perfumeStoryService;

    // 목록 전체보기
    @GetMapping
    public ApiResponse<List<PerfumeStoryResponse>> getAllStories() {
        List<PerfumeStoryResponse> result = perfumeStoryService.getAllStories();
        return ApiResponse.onSuccess(result);
    }

    // 미리보기 카드
    @GetMapping("/preview")
    public ApiResponse<List<PerfumeStoryResponse>> getPreviewStories(
            @RequestParam(defaultValue = "3") int size  // ?size=n 으로 개수 조절 가능
    ) {
        List<PerfumeStoryResponse> result = perfumeStoryService.getPreviewStories(size);
        return ApiResponse.onSuccess(result);
    }

    // 글 상세보기 (전문 확인)
    @GetMapping("/{id}")
    public ApiResponse<PerfumeStoryDetailResponse> getStoryDetail(@PathVariable Long id) {
        PerfumeStoryDetailResponse result = perfumeStoryService.getStoryDetail(id);
        return ApiResponse.onSuccess(result);
    }
}