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

    private  Long productId;

    private String memberEmail;  // 이메일로 저장

    private String perfumeName;

    private String notes;

    private LocalDateTime createdAt;

    public TestResult( Long productId, String memberEmail, String perfumeName, String notes, LocalDateTime createdAt) {
        this.productId = productId;
        this.memberEmail = memberEmail;
        this.perfumeName = perfumeName;
        this.notes = notes;
        this.createdAt = createdAt;
    }
}
