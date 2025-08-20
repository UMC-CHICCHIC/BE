package chic_chic.spring.domain.repository;

import chic_chic.spring.domain.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendProductRepository extends JpaRepository<TestResult, Long> {
    List<TestResult> findTop4ByMemberEmailOrderByCreatedAtDesc(String memberEmail);
}