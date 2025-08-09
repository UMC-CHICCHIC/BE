package chic_chic.spring.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequest {
    @io.swagger.v3.oas.annotations.media.Schema(example = "테스트입니다!")
    private String content;

    @io.swagger.v3.oas.annotations.media.Schema(
            description = "대댓글일 때만 부모 댓글 ID. 최상위 댓글이면 보내지 마세요.",
            required = false,
            example = "123"
    )
    private Long parentCommentId;   // null이면 최상위 댓글
}