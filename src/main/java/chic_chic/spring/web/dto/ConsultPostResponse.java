package chic_chic.spring.web.dto;

import chic_chic.spring.domain.enums.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ConsultPostResponse {

    //최신글 미리보기 응답
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LatestDto{
        private Long consultId;
        private PostType postType;
        private String title;
        private String content;
        private String imageUrl;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HomeLatestDto {
        private LatestDto receivePost;
        private LatestDto givePost;
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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PreviewDto {
        private Long memberId;
        private String nickname;
        private Long consultId;
        private PostType postType;
        private String title;
        private String imageUrl;
        private LocalDateTime dateTime;
    }


    // 향수 추천 상담소 홈 응답
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HomeResponseDto {
        private List<PreviewDto> receivePosts;
        private List<PreviewDto> givePosts;
    }

    //필터링 / 페이징된 리스트
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PagedResponseDto{
        private List<PreviewDto> content;
        private int pageNumber; //현재 페이지
        private int pageSize; //페이지당 게시글 수
        private long totalElements; //전체 게시글 수
        private int totalPages; //전체 페이지 수
        private boolean last; //마지막 페이지 여부
    }
}

