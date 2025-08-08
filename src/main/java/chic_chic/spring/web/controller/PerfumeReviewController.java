package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.ApiResponse;

import chic_chic.spring.service.Review.ReviewService;
import chic_chic.spring.web.dto.perfumeReview.ReviewRequest;
import chic_chic.spring.web.dto.perfumeReview.ReviewResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfumes/{perfumeId}/reviews")
@RequiredArgsConstructor
public class PerfumeReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> listReviews(
            @PathVariable Long perfumeId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<ReviewResponse> reviews = reviewService.listReviews(perfumeId, page - 1, size);
        return ResponseEntity.ok(ApiResponse.onSuccess(reviews));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
            @PathVariable Long perfumeId,
            @RequestBody @Valid ReviewRequest request,
            @AuthenticationPrincipal Long memberId) {
        ReviewResponse resp = reviewService.createReview(perfumeId, request, memberId);
        return ResponseEntity.ok(ApiResponse.onSuccess(resp));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(
            @PathVariable Long perfumeId,
            @PathVariable Long reviewId,
            @RequestBody @Valid ReviewRequest request,
            @AuthenticationPrincipal Long memberId) {
        ReviewResponse resp = reviewService.updateReview(perfumeId, reviewId, request, memberId);
        return ResponseEntity.ok(ApiResponse.onSuccess(resp));
    }

    @DeleteMapping("/{reviewId}")   // 리뷰 삭제
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @PathVariable Long perfumeId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal Long memberId) {
        reviewService.deleteReview(perfumeId, reviewId, memberId);
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }
}
