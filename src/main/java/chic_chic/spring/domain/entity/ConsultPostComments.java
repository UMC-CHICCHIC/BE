package chic_chic.spring.domain.entity;

import chic_chic.spring.domain.common.BaseEntity;
import jakarta.persistence.*;

import lombok.*;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsultPostComments extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false, length = 500)
    private String content;

    // 댓글이면 0, 대댓글이면 1이 들어감
    private Long hierarchy;

    // 대댓글 순서로 데이터 정렬할 때 필요
    private Long orders;

    // 한개의 댓글과 그에 딸린 대댓글을 한 그룹으로 묶기
    @Column(name = "`groups`")
    private Long groups;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultPost_id", nullable = false)
    private ConsultPost consultPost;
}
