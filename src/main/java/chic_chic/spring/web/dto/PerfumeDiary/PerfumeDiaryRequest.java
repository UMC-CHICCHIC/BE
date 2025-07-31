package chic_chic.spring.web.dto.PerfumeDiary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfumeDiaryRequest {

    private String title;
    private String content;
    private Boolean isPublic = true;
}