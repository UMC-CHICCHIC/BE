package chic_chic.spring.web.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedProduct {
    private Long productId;
    private String perfumeName;
    private List<String> recommendedNotes;
}