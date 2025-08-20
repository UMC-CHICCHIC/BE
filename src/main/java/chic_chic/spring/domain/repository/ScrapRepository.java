package chic_chic.spring.domain.repository;

import chic_chic.spring.domain.entity.Member;
import chic_chic.spring.domain.entity.Product;
import chic_chic.spring.domain.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    boolean existsByMemberAndProduct(Member member, Product product);
    void deleteByMemberAndProduct(Member member, Product product);
    List<Scrap> findByMemberId(Long memberId);
}

