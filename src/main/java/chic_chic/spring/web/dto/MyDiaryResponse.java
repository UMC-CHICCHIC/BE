package chic_chic.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MyDiaryResponse {
    private Long diaryId;
    private String title;
    private String nickname;
    private LocalDate createdAt;
    private String imageUrl;
}