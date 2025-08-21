package chic_chic.spring.web.dto;

import chic_chic.spring.domain.entity.PerfumeDiaryComments;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommentResponse {
    private Long commentId;
    private Long diaryId;
    private String content;
    private String nickName;
    private LocalDateTime createdAt;
    private Long parentCommentId;
    private String profileImageUrl;     // 프로필 이미지
    private List<CommentResponse> replies = new ArrayList<>();

    public static CommentResponse fromEntity(PerfumeDiaryComments c) {
        CommentResponse dto = new CommentResponse();
        dto.setCommentId(c.getId());
        dto.setDiaryId(c.getDiary().getId());
        dto.setContent(c.getContent());
        dto.setNickName(c.getUser().getNickname()); // 작성자 닉네임
        dto.setCreatedAt(c.getCreatedAt());
        dto.setParentCommentId(
                c.getParentComment() != null ? c.getParentComment().getId() : null
        );
        dto.setProfileImageUrl(c.getUser().getProfileImageUrl());
        return dto;
    }
}