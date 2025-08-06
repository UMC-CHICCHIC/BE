package chic_chic.spring.web.dto;

import chic_chic.spring.domain.enums.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ConsultPostRequest {
    @NotNull(message = "작성글 유형을 선택해주세요.")
    private PostType postType;

    @NotBlank(message = "제목을 작성해주세요.")
    private String title;

    @NotBlank(message = "내용을 작성해주세요.")
    private String content;

    private String imageUrl;

}
