package chic_chic.spring.service.Review;

import chic_chic.spring.web.dto.perfumeReview.ReviewRequest;
import chic_chic.spring.web.dto.perfumeReview.ReviewResponse;

import java.util.List;

public interface ReviewService {
    List<ReviewResponse> listReviews(Long perfumeId, int page, int size);
    ReviewResponse createReview(Long perfumeId, ReviewRequest request, Long memberId);
    ReviewResponse updateReview(Long perfumeId, Long reviewId, ReviewRequest request, Long memberId);
    void deleteReview(Long perfumeId, Long reviewId, Long memberId);
}
