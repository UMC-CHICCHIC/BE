package chic_chic.spring.service.Review;

import chic_chic.spring.domain.Member;
import chic_chic.spring.domain.Product;
import chic_chic.spring.domain.entity.Review;

import chic_chic.spring.domain.repository.ReviewRepository;
import chic_chic.spring.domain.repository.ProductRepository;
import chic_chic.spring.domain.repository.MemberRepository;
import chic_chic.spring.apiPayload.exception.GeneralException;

import chic_chic.spring.web.dto.perfumeReview.ReviewRequest;
import chic_chic.spring.web.dto.perfumeReview.ReviewResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static chic_chic.spring.apiPayload.code.status.ErrorStatus.*;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ProductRepository productRepository,
                             MemberRepository memberRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional(readOnly = true)
    // 첫 페이지만 캐시 (0-based)
    @Cacheable(
            cacheNames = "reviews",
            key = "'p:' + #p0 + ':page:' + #p1 + ':size:' + #p2",
            condition = "#p1 == 0"        // 첫 페이지(0)만
    )
    public List<ReviewResponse> listReviews(Long perfumeId, int page, int size) {
        return reviewRepository.findByPerfumeId(
                        perfumeId,
                        PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
                )
                .stream()
                .map(r -> ReviewResponse.builder()
                        .id(r.getId())
                        .memberNickname(r.getMember().getNickname())
                        .rating(r.getRating())
                        .content(r.getContent())
                        .createdAt(r.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "product:detail", key = "#perfumeId"),
            @CacheEvict(cacheNames = "reviews", allEntries = true) // 목록 캐시 무효화
    })  public ReviewResponse createReview(Long perfumeId, ReviewRequest request, Long memberId) {
        Product product = productRepository.findById(perfumeId)
                .orElseThrow(() -> new GeneralException(PERFUME_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(MEMBER_NOT_FOUND));

        Review review = Review.builder()
                .perfumeId(perfumeId)
                .member(member)
                .rating(request.getRating())
                .content(request.getContent())
                .build();
        reviewRepository.save(review);

        long count = reviewRepository.countByPerfumeId(perfumeId);
        long sum   = reviewRepository.sumRatingByPerfumeId(perfumeId);
        double avg = (double) sum / count;
        product.setReviewCount(count);
        product.setAverageRating(avg);
        productRepository.save(product);

        return ReviewResponse.builder()
                .id(review.getId())
                .memberNickname(member.getNickname())
                .rating(review.getRating())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .build();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "product:detail", key = "#perfumeId"),
            @CacheEvict(cacheNames = "reviews", allEntries = true) // 단순화: 모든 페이지 무효화
            // 필요시 page 0만 지우려면 키 규칙에 맞춰 개별 키 evict 로 커스터마이즈
    })
    public ReviewResponse updateReview(Long perfumeId, Long reviewId, ReviewRequest request, Long memberId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new GeneralException(REVIEW_NOT_FOUND));
        if (!review.getPerfumeId().equals(perfumeId)) throw new GeneralException(REVIEW_NOT_FOUND);
        if (!review.getMember().getId().equals(memberId)) throw new GeneralException(UNAUTHORIZED_REVIEW_MODIFY);

        review.setRating(request.getRating());
        review.setContent(request.getContent());
        reviewRepository.save(review);

        long count = reviewRepository.countByPerfumeId(perfumeId);
        long sum   = reviewRepository.sumRatingByPerfumeId(perfumeId);
        Product product = productRepository.findById(perfumeId)
                .orElseThrow(() -> new GeneralException(PERFUME_NOT_FOUND));
        product.setReviewCount(count);
        product.setAverageRating((double) sum / count);
        productRepository.save(product);

        return ReviewResponse.builder()
                .id(review.getId())
                .memberNickname(review.getMember().getNickname())
                .rating(review.getRating())
                .content(review.getContent())
                .createdAt(review.getUpdatedAt())
                .build();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "product:detail", key = "#perfumeId"),
            @CacheEvict(cacheNames = "reviews", allEntries = true)
    })
    public void deleteReview(Long perfumeId, Long reviewId, Long memberId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new GeneralException(REVIEW_NOT_FOUND));
        if (!review.getPerfumeId().equals(perfumeId)) throw new GeneralException(REVIEW_NOT_FOUND);
        if (!review.getMember().getId().equals(memberId)) throw new GeneralException(UNAUTHORIZED_REVIEW_MODIFY);
        reviewRepository.delete(review);

        long count = reviewRepository.countByPerfumeId(perfumeId);
        long sum   = reviewRepository.sumRatingByPerfumeId(perfumeId);
        Product product = productRepository.findById(perfumeId)
                .orElseThrow(() -> new GeneralException(PERFUME_NOT_FOUND));
        product.setReviewCount(count);
        product.setAverageRating(count > 0 ? (double) sum / count : 0);
        productRepository.save(product);
    }
}