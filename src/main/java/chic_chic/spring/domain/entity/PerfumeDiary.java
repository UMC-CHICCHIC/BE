package chic_chic.spring.domain.entity;

import chic_chic.spring.domain.Member;
import chic_chic.spring.domain.common.BaseEntity;
import jakarta.persistence.*;

@Entity
public class PerfumeDiary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // 외래키명
    private Member member;

    private String title;
    private String content;
    private Boolean isPublic = true;
    private String imageUrl;
}