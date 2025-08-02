package chic_chic.spring.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "test_result")
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Long testId;
    
    private Integer perfumeId;
    private Long memberId;
    private String perfumeName;
    private String notes;
    private LocalDateTime createdAt;

    public TestResult(Integer perfumeId, Long memberId, String perfumeName, String notes, LocalDateTime createdAt) {
        this.perfumeId = perfumeId;
        this.memberId = memberId;
        this.perfumeName = perfumeName;
        this.notes = notes;
        this.createdAt = createdAt;
    }
}