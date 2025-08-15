package chic_chic.spring.web.dto.perfumereview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private Long id;
    private String memberNickname;
    private int rating;
    private String content;
    private LocalDateTime createdAt;
}