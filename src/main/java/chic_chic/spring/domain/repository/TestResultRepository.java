package chic_chic.spring.domain.repository;

import chic_chic.spring.domain.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    List<TestResult> findAllByMemberEmailAndCreatedAt(String memberEmail, LocalDateTime createdAt);
}