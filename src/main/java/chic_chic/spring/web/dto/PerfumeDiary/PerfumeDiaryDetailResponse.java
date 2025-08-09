package chic_chic.spring.web.dto.PerfumeDiary;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PerfumeDiaryDetailResponse {
    private Long id;
    private String title;
    private String content;
    private String nickName;
    private String imageUrl;
    private LocalDate createdAt;
    private Boolean isPublic;
}
