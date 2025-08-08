package chic_chic.spring.domain.repository;

import chic_chic.spring.domain.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPerfumeIdOrderByCreatedAtDesc(Long perfumeId);
    long countByPerfumeId(Long perfumeId);

    @Query("SELECT COALESCE(SUM(r.rating),0) FROM Review r WHERE r.perfumeId = :perfumeId")
    long sumRatingByPerfumeId(Long perfumeId);

    Page<Review> findByPerfumeId(Long perfumeId, Pageable pageable);
}
