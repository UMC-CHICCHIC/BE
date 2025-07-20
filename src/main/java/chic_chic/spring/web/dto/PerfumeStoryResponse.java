package chic_chic.spring.web.dto;

import chic_chic.spring.domain.entity.PerfumeStory;
import lombok.Builder;
import lombok.Getter;

// 향수 이야기 (전체 조회)
@Builder
@Getter
public class PerfumeStoryResponse {
    private Long storyId;
    private String title;
    private String summary;
    private String thumbnailUrl;

    public static PerfumeStoryResponse fromEntity(PerfumeStory story) {
        return PerfumeStoryResponse.builder()
                .storyId(story.getStoryId())
                .title(story.getTitle())
                .summary(story.getSummary())
                .thumbnailUrl(story.getThumbnailUrl())
                .build();
    }
}