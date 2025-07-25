package chic_chic.spring.web.dto;

import chic_chic.spring.domain.enums.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ConsultPostResponse {

    //최신글 미리보기 응답
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PreviewDto{
        private Long consultId;
        private PostType postType;
        private String title;
        private String content;
        private String imageUrl;
    }

    //게시글 보기
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EntirePostDto{
        private Long memberId;
        private String nickname;
        private Long consultPostId;
        private PostType postType;
        private String title;
        private String content;
        private String imageUrl;
        private LocalDateTime dateTime;
    }

    // 향수 추천 상담소 홈 응답
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConsultPostSummaryDto{
        private Long memberId;
        private String nickname;
        private Long consultPostId;
        private PostType postType;
        private String title;
        private String imageUrl;
        private LocalDateTime dateTime;
    }

    //필터링 페이지 응답
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConsultPostFilteredDto{
        private Long memberId;
        private String nickname;
        private Long consultPostId;
        private String title;
        private String imageUrl;
        private LocalDateTime dateTime;
    }
}

