package chic_chic.spring.domain.repository;

import chic_chic.spring.domain.entity.PerfumeDiary;
import chic_chic.spring.domain.entity.PerfumeDiaryComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfumeDiaryCommentRepository extends JpaRepository<PerfumeDiaryComments, Long> {
    List<PerfumeDiaryComments> findByDiaryAndParentCommentIsNullOrderByCreatedAt(PerfumeDiary diary);

}