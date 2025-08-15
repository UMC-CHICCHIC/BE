package chic_chic.spring.service.review;

import chic_chic.spring.web.dto.perfumereview.ReviewRequest;
import chic_chic.spring.web.dto.perfumereview.ReviewResponse;

import java.util.List;

public interface ReviewService {
    List<ReviewResponse> listReviews(Long perfumeId, int page, int size);
    ReviewResponse createReview(Long perfumeId, ReviewRequest request, Long memberId);
    ReviewResponse updateReview(Long perfumeId, Long reviewId, ReviewRequest request, Long memberId);
    void deleteReview(Long perfumeId, Long reviewId, Long memberId);
}
