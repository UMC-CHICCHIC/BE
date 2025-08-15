package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.ApiResponse;

import chic_chic.spring.apiPayload.exception.GeneralException;
import chic_chic.spring.domain.entity.Member;
import chic_chic.spring.domain.repository.MemberRepository;
import chic_chic.spring.service.review.ReviewService;
import chic_chic.spring.web.dto.perfumereview.ReviewRequest;
import chic_chic.spring.web.dto.perfumereview.ReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static chic_chic.spring.apiPayload.code.status.ErrorStatus.*;

@Tag(name = "PerfumeReview", description = "향수 상세조회 및 리뷰 API")
@RestController
@RequestMapping("/perfumes/{perfumeId}/reviews")
@RequiredArgsConstructor
public class PerfumeReviewController {
    private final ReviewService reviewService;
    private final MemberRepository memberRepository;

    @Operation(
            summary = "리뷰 목록 조회",
            description = "향수별 리뷰를 최신순으로 조회합니다."
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> listReviews(
            @PathVariable Long perfumeId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<ReviewResponse> reviews = reviewService.listReviews(perfumeId, page - 1, size);
        return ResponseEntity.ok(ApiResponse.onSuccess(reviews));
    }

    @Operation(summary = "향수 리뷰 작성", description = "해당 향수의 리뷰를 작성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
            @PathVariable Long perfumeId,
            @RequestBody @Valid ReviewRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(MEMBER_NOT_FOUND));
        ReviewResponse resp = reviewService.createReview(perfumeId, request, member.getId());
        return ResponseEntity.ok(ApiResponse.onSuccess(resp));
    }

    @Operation(summary = "향수 리뷰 수정", description = "해당 리뷰를 수정합니다.")
    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(
            @PathVariable Long perfumeId,
            @PathVariable Long reviewId,
            @RequestBody @Valid ReviewRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(MEMBER_NOT_FOUND));
        ReviewResponse resp = reviewService.updateReview(perfumeId, reviewId, request, member.getId());
        return ResponseEntity.ok(ApiResponse.onSuccess(resp));
    }

    @Operation(summary = "향수 리뷰 삭제", description = "해당 리뷰를 작성합니다.")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @PathVariable Long perfumeId,
            @PathVariable Long reviewId,
            Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(MEMBER_NOT_FOUND));
        reviewService.deleteReview(perfumeId, reviewId, member.getId());
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }
}