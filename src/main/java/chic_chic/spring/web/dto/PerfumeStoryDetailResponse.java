package chic_chic.spring.web.dto;


import chic_chic.spring.domain.entity.PerfumeStory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// 향수 이야기 상세 조회 (전체글)
@Getter
@Builder
public class PerfumeStoryDetailResponse {

    private Long storyId;
    private String title;
    private String summary;
    private String content;
    private String thumbnailUrl;
    private LocalDateTime createdAt;

    public static PerfumeStoryDetailResponse fromEntity(PerfumeStory story) {
        return PerfumeStoryDetailResponse.builder()
                .storyId(story.getStoryId())
                .title(story.getTitle())
                .summary(story.getSummary())
                .content(story.getContent())
                .thumbnailUrl(story.getThumbnailUrl())
                .createdAt(story.getCreatedAt())
                .build();
    }
}