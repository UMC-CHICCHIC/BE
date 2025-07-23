package chic_chic.spring.domain.repository;

import chic_chic.spring.domain.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    List<TestResult> findAllByUserIdAndCreatedAt(Long userId, LocalDateTime createdAt);
}