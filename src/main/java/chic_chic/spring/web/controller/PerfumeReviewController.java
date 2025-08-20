package chic_chic.spring.web.controller;

import chic_chic.spring.apiPayload.ApiResponse;

import chic_chic.spring.apiPayload.exception.GeneralException;
import chic_chic.spring.domain.entity.Member;
import chic_chic.spring.domain.repository.MemberRepository;
import chic_chic.spring.service.review.ReviewService;
import chic_chic.spring.web.dto.perfumereview.ReviewRequest;
import chic_chic.spring.web.dto.perfumereview.ReviewResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static chic_chic.spring.apiPayload.code.status.ErrorStatus.*;

@RestController
@RequestMapping("/perfumes/{perfumeId}/reviews")
@RequiredArgsConstructor
public class PerfumeReviewController {
    private final ReviewService reviewService;
    private final MemberRepository memberRepository;

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
            Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(MEMBER_NOT_FOUND));
        ReviewResponse resp = reviewService.createReview(perfumeId, request, member.getId());
        return ResponseEntity.ok(ApiResponse.onSuccess(resp));
    }

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