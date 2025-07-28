package chic_chic.spring.domain.entity;

import chic_chic.spring.domain.Member;
import chic_chic.spring.domain.common.BaseEntity;
import jakarta.persistence.*;

@Entity
public class PerfumeDiaryComments extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private PerfumeDiary diary;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private PerfumeDiaryComments parent;  // 대댓글용
}