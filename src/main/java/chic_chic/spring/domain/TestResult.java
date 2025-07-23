package chic_chic.spring.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testId;
    
    private Integer perfumeId;
    private Long userId;
    private String perfumeName;
    private String notes;
    private LocalDateTime createdAt;

    //파라미터 존재 생성자
    public TestResult(Integer perfumeId, Long userId, String perfumeName, String notes, LocalDateTime createdAt) {
        this.perfumeId = perfumeId;
        this.userId = userId;
        this.perfumeName = perfumeName;
        this.notes = notes;
        this.createdAt = createdAt;
    }
}