package chic_chic.spring.domain.entity;

import chic_chic.spring.domain.Member;
import chic_chic.spring.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerfumeDiary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // 외래키명
    private Member user;

    private String title;
    private String content;
    private Boolean isPublic = true;
    private String imageUrl;
}