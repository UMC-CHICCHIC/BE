package chic_chic.spring.domain;

import chic_chic.spring.domain.common.BaseEntity;
import chic_chic.spring.domain.enums.PostType;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ConsultPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consultPostId;

    private String title;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String imageUrl; //S3에 업로드한 이미지 주소

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Member member;




}
