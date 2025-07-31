package chic_chic.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private Long commentId;
    private Long diaryId;
    private String content;
    private String nickName;
    private LocalDateTime createdAt;
    private Long parentCommentId;

}