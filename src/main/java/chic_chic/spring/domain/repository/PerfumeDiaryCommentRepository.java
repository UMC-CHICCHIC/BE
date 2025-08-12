package chic_chic.spring.domain.repository;

import chic_chic.spring.domain.entity.PerfumeDiary;
import chic_chic.spring.domain.entity.PerfumeDiaryComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PerfumeDiaryCommentRepository extends JpaRepository<PerfumeDiaryComments, Long> {
    // 시간순으로 댓글 출력 (대댓글 포함)
    List<PerfumeDiaryComments> findByDiaryAndParentCommentIsNullOrderByCreatedAt(PerfumeDiary diary);

    // 대댓글 포함 전체 댓글 조회
    List<PerfumeDiaryComments> findByDiaryOrderByCreatedAtAsc(PerfumeDiary diary);

    // 부모 검증용: 해당 diary에 속한 특정 댓글
    Optional<PerfumeDiaryComments> findByIdAndDiaryId(Long id, Long diaryId);
}