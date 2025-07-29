package chic_chic.spring.domain.repository;

import chic_chic.spring.domain.entity.PerfumeDiary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfumeDiaryRepository extends JpaRepository<PerfumeDiary, Long> {

    // 내 게시글 리스트 (최신순)
    Page<PerfumeDiary> findByUser_IdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    // 공개 게시글 리스트 (최신순)
    Page<PerfumeDiary> findByIsPublicTrueOrderByCreatedAtDesc(Pageable pageable);

    // 특정 게시글 + 작성자 체크
    Optional<PerfumeDiary> findByIdAndUser_Id(Long id, Long userId);

}