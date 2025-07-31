package chic_chic.spring.domain.repository;

import chic_chic.spring.domain.ConsultPost;
import chic_chic.spring.domain.enums.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConsultPostRepository extends JpaRepository<ConsultPost, Long> {

    Optional<ConsultPost> findByConsultPostId(Long consultPostId);

    //postType 별 최신 1개 조회
    Optional<ConsultPost> findFirstByPostTypeOrderByCreatedAtDesc(PostType postType);

    List<ConsultPost> findTop2ByPostTypeOrderByCreatedAtDesc(PostType postType);

    Page<ConsultPost> findAllByPostType(PostType postType, Pageable pageable);
}
