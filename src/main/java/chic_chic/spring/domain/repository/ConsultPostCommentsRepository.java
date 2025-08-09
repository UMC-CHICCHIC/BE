package chic_chic.spring.domain.repository;

import chic_chic.spring.domain.entity.ConsultPostComments;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsultPostCommentsRepository extends JpaRepository<ConsultPostComments, Long> {
    // 본문 id에 소속된 모든 댓글들을 정렬 조회
    @EntityGraph(attributePaths = {"consultPost", "member"})
    @Query("select c from ConsultPostComments c where c.consultPost.consultPostId = :consultPostId order by c.groups, c.orders")
    List<ConsultPostComments> findAlignedConsultPostCommentsByPostId(@Param("consultPostId") Long consultPostId);


    @Query("select max(c.groups) from ConsultPostComments c where c.consultPost.consultPostId = :consultPostId")
    Long findMaxGroupByPostId(@Param("consultPostId") Long postId);

    // 가장 큰 order 가져오기
    @EntityGraph(attributePaths = {"consultPost", "member"})
    @Query("select max (c.orders) from ConsultPostComments c where c.consultPost.consultPostId = :consultPostId and c.groups = :groups")
    Long findMaxConsultPostCommentsOrder(@Param("consultPostId") Long consultPostId, @Param("groups") Long groups);
}
