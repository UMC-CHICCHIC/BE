package chic_chic.spring.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequest {
    private String content;
    private Long parentCommentId;   // 대댓글 등록 시 부모 댓글 ID (최상위 댓글일 땐 null)
}