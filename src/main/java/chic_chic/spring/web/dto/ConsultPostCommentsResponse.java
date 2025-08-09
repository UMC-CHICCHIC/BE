package chic_chic.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ConsultPostCommentsResponse {

    //댓글 작성 후 반환
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentResponseDto{
        private Long commentId;
        private Long groupId; //대댓글 작성할 때 필요
    }

    // 목록 조회 시 사용
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentListDto{
        private Long memberId;
        private String nickname;
        private String content;
        private Long hierarchy;
        private Long order;
        private Long group;
        private LocalDateTime dateTime;
    }

    @Getter
    @Builder
    public static class CommentThreadDto{
        private Long groupId;
        private CommentListDto parent;
        private List<CommentListDto> replies;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentResultDto{
        private List<CommentThreadDto> content;
    }

}
